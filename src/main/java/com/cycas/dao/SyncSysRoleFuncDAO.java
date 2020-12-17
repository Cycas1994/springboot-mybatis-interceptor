package com.cycas.dao;

import com.cycas.entity.SyncSysRoleFuncDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyncSysRoleFuncDAO {


    @Insert({
            "<script>",
            "insert into t_sys_role_func (",
            "t_sys_role_id, t_sys_func_id,",
            "create_time, update_time)",
            "values ",
            "(#{tSysRoleId,jdbcType=INTEGER}, #{tSysFuncId,jdbcType=INTEGER},",
            "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})",
            "</script>",
    })
    int insert(SyncSysRoleFuncDTO sysRoleFuncs);

    @Insert({
            "<script>",
            "insert into t_sys_role_func (",
            "t_sys_role_id, t_sys_func_id,",
            "create_time, update_time)",
            "values ",
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">",
            "(#{item.tSysRoleId,jdbcType=INTEGER}, #{item.tSysFuncId,jdbcType=INTEGER},",
            "#{item.createTime,jdbcType=TIMESTAMP}, #{item.updateTime,jdbcType=TIMESTAMP})",
            "</foreach>",
            "</script>",
    })
    int insertBatch(@Param("list") List<SyncSysRoleFuncDTO> sysRoleFuncs);

    @Delete({
            "delete from t_sys_role_func",
    })
    int deleteAll();
}
