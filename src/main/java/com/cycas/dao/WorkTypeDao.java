package com.cycas.dao;

import com.cycas.entity.BaseServiceWorkType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author naxin
 * @Description:
 * @date 2020/12/910:43
 */
@Repository
public interface WorkTypeDao {

    @Insert({
            "<script>",
            "insert into base_service_work_type (company_id, name, ",
            "code, system_flag, desc_info, ",
            "status_code, seq, creater, ",
            "create_time, updater, ",
            "update_time, delete_flag, remote_mapper_id)",
            "values  ",
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">",
            "(#{item.companyId,jdbcType=INTEGER}, #{item.name,jdbcType=VARCHAR}, ",
            "#{item.code,jdbcType=VARCHAR}, #{item.systemFlag,jdbcType=CHAR}, #{item.descInfo,jdbcType=VARCHAR}, ",
            "#{item.statusCode,jdbcType=CHAR}, #{item.seq,jdbcType=INTEGER}, #{item.creater,jdbcType=VARCHAR}, ",
            "#{item.createTime,jdbcType=TIMESTAMP}, #{item.updater,jdbcType=VARCHAR}, ",
            "#{item.updateTime,jdbcType=TIMESTAMP}, #{item.deleteFlag,jdbcType=CHAR}, #{item.remoteMapperId,jdbcType=INTEGER})",
            "</foreach>",
            "</script>",
    })
    int insertBatch(@Param("list") List<BaseServiceWorkType> saveWorkTypes);

    @Insert({
            "<script>",
            "insert into base_service_work_type (company_id, name, ",
            "code, system_flag, desc_info, ",
            "status_code, seq, creater, ",
            "create_time, updater, ",
            "update_time, delete_flag, remote_mapper_id)",
            "values  ",
            "(#{companyId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{code,jdbcType=VARCHAR}, #{systemFlag,jdbcType=CHAR}, #{descInfo,jdbcType=VARCHAR}, ",
            "#{statusCode,jdbcType=CHAR}, #{seq,jdbcType=INTEGER}, #{creater,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{updater,jdbcType=VARCHAR}, ",
            "#{updateTime,jdbcType=TIMESTAMP}, #{deleteFlag,jdbcType=CHAR}, #{remoteMapperId,jdbcType=INTEGER})",
            "</script>",
    })
    int insert(BaseServiceWorkType saveWorkTypes);

    @Update({
            "<script>",
            "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">",
            "update base_service_work_type",
            "set company_id = #{item.companyId,jdbcType=INTEGER},",
            "name = #{item.name,jdbcType=VARCHAR},",
            "code = #{item.code,jdbcType=VARCHAR},",
            "system_flag = #{item.systemFlag,jdbcType=CHAR},",
            "desc_info = #{item.descInfo,jdbcType=VARCHAR},",
            "status_code = #{item.statusCode,jdbcType=CHAR},",
            "seq = #{item.seq,jdbcType=INTEGER},",
            "creater = #{item.creater,jdbcType=VARCHAR},",
            "create_time = #{item.createTime,jdbcType=TIMESTAMP},",
            "updater = #{item.updater,jdbcType=VARCHAR},",
            "update_time = #{item.updateTime,jdbcType=TIMESTAMP},",
            "delete_flag = #{item.deleteFlag,jdbcType=CHAR},",
            "remote_mapper_id = #{item.remoteMapperId,jdbcType=INTEGER}",
            "where id = #{item.id,jdbcType=INTEGER}",
            "</foreach>",
            "</script>",
    })
    int updateBatch(@Param("list") List<BaseServiceWorkType> updateWorkTypes);
}
