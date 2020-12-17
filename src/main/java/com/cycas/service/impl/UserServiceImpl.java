package com.cycas.service.impl;

import com.cycas.dao.UserDao;
import com.cycas.dao.WorkTypeDao;
import com.cycas.entity.User;
import com.cycas.service.UserService;
import com.cycas.service.WorkTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private WorkTypeService workTypeService;

    @Override
    @Transactional
    public int saveUser() {
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setName("name" + i);
            user.setNote("note" + i);
            userMapper.insert(user);
            workTypeService.insert(i);
        }
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Long id, String monthStr) {
        return userMapper.selectByPrimaryKey(id, monthStr);
    }

    @Override
    public User queryByCompanyId(Long id, String companyId) {
        return userMapper.queryByCompanyId(id, companyId);
    }

    @Override
    public PageInfo<User> queryUsersByCondition(String name, String companyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.queryUsersByCondition(name, companyId);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

}
