package com.cycas.sharding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CompanyShardStrategy extends BaseShardStrategy {

    private static final Logger logger = LoggerFactory.getLogger(CompanyShardStrategy.class);

    public CompanyShardStrategy() {
        super();
    }

    public String calcTableName(String currentTableName, Map<String, Object> parameter, String mapperMethod) {
        if (parameter == null) {
            logger.error("the shard parameter should not null");
            return null;
        } else {
            Integer companyId = this.getCompanyId(parameter);
            if (companyId == null) {
                logger.error("the shard parameter, companyId should not null, method: " + mapperMethod);
                return null;
            } else if (companyId == 0) {
                logger.error("the shard parameter, companyId should not be zero, method: " + mapperMethod);
                return null;
            } else {
                return currentTableName + "_" + companyId;
            }
        }
    }

}
