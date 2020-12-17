package com.cycas.entity;


import java.util.Date;

public class SyncSysRoleFuncDTO {

    /**
     * 角色id
     */
    private Integer tSysRoleId;

    /**
     * 功能菜单id
     */
    private Integer tSysFuncId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近修改时间
     */
    private Date updateTime;

    public Integer gettSysRoleId() {
        return tSysRoleId;
    }

    public void settSysRoleId(Integer tSysRoleId) {
        this.tSysRoleId = tSysRoleId;
    }

    public Integer gettSysFuncId() {
        return tSysFuncId;
    }

    public void settSysFuncId(Integer tSysFuncId) {
        this.tSysFuncId = tSysFuncId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
