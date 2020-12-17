package com.cycas.sharding;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Name;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.util.*;

@Intercepts({@Signature(
    type = StatementHandler.class,
    method ="prepare",
    args = {Connection.class, Integer.class}
)})
public class TableShardingInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(TableShardingInterceptor.class);
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    private List<String> companyList = new ArrayList();
    private List<String> shardByCompanyList = new ArrayList<>();
    private List<String> shardByMonthList = new ArrayList<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        logger.debug("== sharding intercept");
        String originalSql = null;
        try {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            if (statementHandler == null) {
                logger.error("invalid statementHandler");
                return invocation.proceed();
            }

            MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                    SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

            originalSql = (String) metaObject.getValue("delegate.boundSql.sql");
            if (StringUtils.isBlank(originalSql)) {
                logger.error("invalid originalSql");
                return invocation.proceed();
            }

            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            if (mappedStatement == null) {
                logger.error("invalid mappedStatement");
                return invocation.proceed();
            }

            BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
            if (boundSql == null) {
                logger.error("invalid boundSql");
                return invocation.proceed();
            }

            Map<String, Object> parameter = getParameterFromMappedStatement(mappedStatement, boundSql);
            Object mapperMethod = metaObject.getValue("delegate.parameterHandler.mappedStatement.id");
            logger.debug("==prepare sharding sql: {} \n, mapperMethod: {} \n, parameter: {}",
                    new Object[]{originalSql, JSON.toJSONString(mapperMethod), JSON.toJSONString(parameter)});
            this.shardingTotal(metaObject, parameter, originalSql, JSON.toJSONString(mapperMethod));
        } catch (Exception e) {
            logger.error("fail to sharding the sql" + originalSql, e);
        }

        return invocation.proceed();
    }

    private void shardingTotal(MetaObject metaObject, Map<String, Object> parameter, String originalSql, String mapperMethod) {
        Map<String, String> tableNameMapping = new HashMap();
        MySqlStatementParser parser = new MySqlStatementParser(originalSql);
        SQLStatement statement = parser.parseStatement();
        Set<Name> tables = this.getTables(statement);
        if (tables == null) {
            logger.error("fail to get involved table names, sql {}", originalSql);
        } else {
            Iterator var9 = tables.iterator();

            while(var9.hasNext()) {
                Name table = (Name)var9.next();
                String tableName = table.getName();
                String newTableName = this.execSharding(tableName, parameter, mapperMethod);
                if (StringUtils.isNotBlank(newTableName)) {
                    tableNameMapping.put(tableName, newTableName);
                }
            }

            if (tableNameMapping.size() != 0) {
                StringBuilder newSql = new StringBuilder();
                SQLASTOutputVisitor outputVisitor = SQLUtils.createOutputVisitor(newSql, "mysql");
                Iterator var14 = tableNameMapping.entrySet().iterator();

                while(var14.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry)var14.next();
                    outputVisitor.addTableMapping((String)entry.getKey(), (String)entry.getValue());
                }

                statement.accept(outputVisitor);
                metaObject.setValue("delegate.boundSql.sql", newSql.toString());
            }
        }
    }

    private String execSharding(String tableName, Map<String, Object> parameter, String mapperMethod) {
        if (this.shardByCompanyList.contains(tableName)) {
            CompanyShardStrategy shardStrategy = new CompanyShardStrategy();
            return shardStrategy.calcTableName(tableName, parameter, mapperMethod);
        } else if (this.shardByMonthList.contains(tableName)) {
            MonthShardStrategy monthShardStrategy = new MonthShardStrategy();
            return monthShardStrategy.calcTableName(tableName, parameter, mapperMethod);
        } else {
            logger.debug("ignore table {}, not in the configure", tableName);
            return null;
        }
    }

    private Set<TableStat.Name> getTables(SQLStatement statement) {
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);
        Map<TableStat.Name, TableStat> tables = visitor.getTables();
        return tables != null ? tables.keySet() : null;
    }

    private Map<String, Object> getParameterFromMappedStatement(MappedStatement ms, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        HashMap paramMap;
        if (parameterObject == null) {
            paramMap = new HashMap();
        } else if (parameterObject instanceof Map) {
            paramMap = new HashMap();
            paramMap.putAll((Map)parameterObject);
        } else {
            paramMap = new HashMap();
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass());
            MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
            if (!hasTypeHandler) {
                String[] var7 = metaObject.getGetterNames();
                int var8 = var7.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    String name = var7[var9];
                    paramMap.put(name, metaObject.getValue(name));
                }
            }

            if (boundSql.getParameterMappings() != null && boundSql.getParameterMappings().size() > 0) {
                Iterator var11 = boundSql.getParameterMappings().iterator();

                ParameterMapping parameterMapping;
                String name;
                do {
                    do {
                        if (!var11.hasNext()) {
                            return paramMap;
                        }

                        parameterMapping = (ParameterMapping)var11.next();
                        name = parameterMapping.getProperty();
                    } while(paramMap.get(name) != null);
                } while(!hasTypeHandler && !parameterMapping.getJavaType().equals(parameterObject.getClass()));

                paramMap.put(name, parameterObject);
            }
        }

        return paramMap;
    }

    @Override
    public Object plugin(Object o) {

        return o instanceof StatementHandler ? Plugin.wrap(o, this) : o;
    }

    @Override
    public void setProperties(Properties properties) {

        this.parseShardByCompany(properties.getProperty("shardByCompany"));
        this.parseShardByMonth(properties.getProperty("shardByMonth"));
    }

    private void parseShardByCompany(String shardByCompany) {

        logger.debug("ShardByCompany {}", shardByCompany);
        if (!StringUtils.isBlank(shardByCompany)) {
            String[] splits = shardByCompany.split(",");
            String[] var3 = splits;
            int var4 = splits.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String split = var3[var5];
                if (StringUtils.isNotBlank(split)) {
                    this.shardByCompanyList.add(split.trim());
                }
            }

        }
    }

    private void parseShardByMonth(String shardByMonth) {

        logger.debug("ShardByMonth {}", shardByMonth);
        if (!StringUtils.isBlank(shardByMonth)) {
            String[] splits = shardByMonth.split(",");
            String[] var3 = splits;
            int var4 = splits.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String split = var3[var5];
                if (StringUtils.isNotBlank(split)) {
                    this.shardByMonthList.add(split.trim());
                }
            }

        }
    }
}
