package com.cycas.sharding;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BaseShardStrategy {

    private static final Logger logger = LoggerFactory.getLogger(BaseShardStrategy.class);

    public BaseShardStrategy() {
    }

    protected Integer getCompanyId(Map<String, Object> parameter) {
        if (parameter == null) {
            logger.error("the shard CompanyId parameter should not null");
            logger.error("the shard CompanyId parameter should not null");
            return null;
        } else {
            Object companyId = parameter.get("companyId");
            if (companyId == null) {
                logger.error("the shard parameter, companyId should not null");
                return null;
            } else {
                Integer useComanyId = null;
                if (companyId instanceof Integer) {
                    useComanyId = (Integer)companyId;
                }

                if (companyId instanceof String) {
                    try {
                        useComanyId = Integer.parseInt((String)companyId);
                    } catch (Exception var5) {
                        logger.error("fail to parse companyId: " + companyId, var5);
                    }
                }

                if (useComanyId == null) {
                    logger.error("the shard parameter companyId should not null");
                    return null;
                } else {
                    return useComanyId;
                }
            }
        }
    }

    protected String getMonth(Map<String, Object> parameter) {
        if (parameter == null) {
            logger.error("the shard monthStr parameter should not null");
            logger.error("the shard monthStr parameter should not null");
            return null;
        } else {
            Object monthStr = parameter.get("monthStr");
            if (monthStr instanceof String) {
                String newStr = (String)monthStr;
                if (StringUtils.isBlank(newStr)) {
                    logger.error("the shard parameter, monthStr should not null");
                    return null;
                } else {
                    return newStr;
                }
            } else {
                return null;
            }
        }
    }

}
