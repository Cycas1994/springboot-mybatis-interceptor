package com.cycas.service;

import com.cycas.entity.User;
import com.github.pagehelper.PageInfo;


public interface UserService {

    int saveUser(Long i);

    User selectByPrimaryKey(Long id, String monthStr);

    User queryByCompanyId(Long id, String companyId);

    PageInfo<User> queryUsersByCondition(String name, String companyId, int pageNum, int pageSize);

    void select();
}
