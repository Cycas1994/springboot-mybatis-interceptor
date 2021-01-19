package com.cycas.dao;

import com.cycas.entity.BaseServiceWorkType;
import com.cycas.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into t_user(id,note,name) values ",
            "(#{id},#{note,jdbcType=VARCHAR},#{name,jdbcType=VARCHAR})"
    })
    int insert(User record);

    int insertSelective(User record);

    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User selectByPrimaryKey(@Param("id") Long id, @Param("monthStr") String monthStr);

    @Select("SELECT * FROM t_user ")
    List<User> selectAll();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User queryByCompanyId(@Param("id") Long id, @Param("companyId") String companyId);

    @Select("SELECT * FROM t_user WHERE name like concat('%', #{name}, '%')")
    List<User> queryUsersByCondition(@Param("name") String name, @Param("companyId") String companyId);

}