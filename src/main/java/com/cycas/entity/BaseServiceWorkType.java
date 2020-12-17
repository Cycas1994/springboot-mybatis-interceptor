package com.cycas.entity;

import java.util.Date;

/**
 * @Author: Shi Ji Yong
 * @Description：工单类型
 * @Date: 2019-08-01 14:29
 */
public class BaseServiceWorkType{
    private Integer id;

    private Integer companyId;

    private String name;

    private String code;

    private String systemFlag;

    private String descInfo;

    /**
     * 默认：Y
     */
    private String statusCode;

    private String creater;

    private Date createTime;

    private String updater;

    private Date updateTime;

    /**
     * 默认：N
     */
    private String deleteFlag;

    private Integer seq;

    /**
     * 远程库表的主键id
     */
    private Integer remoteMapperId;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getRemoteMapperId() {
        return remoteMapperId;
    }

    public void setRemoteMapperId(Integer remoteMapperId) {
        this.remoteMapperId = remoteMapperId;
    }


}
