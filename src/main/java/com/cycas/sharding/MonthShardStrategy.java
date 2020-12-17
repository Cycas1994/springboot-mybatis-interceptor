package com.cycas.sharding;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MonthShardStrategy extends BaseShardStrategy {

    private static final Logger logger = LoggerFactory.getLogger(MonthShardStrategy.class);

    public MonthShardStrategy() {
        super();
    }

    public String calcTableName(String tableName, Map<String, Object> parameter, String mapperMethod) {
        if (parameter == null) {
            logger.error("the shard parameter should not null");
            return null;
        } else {
            String month = this.getMonth(parameter);
            if (StringUtils.isBlank(month)) {
                logger.error("分表预警--MonthShardStrategy，没有配置monthStr, method: " + mapperMethod);
                return null;
            } else {
                return tableName + "_" + month;
            }
        }
    }
}
