package com.cycas.controller;

import com.cycas.entity.SyncSysRoleFuncDTO;
import com.cycas.entity.User;
import com.cycas.service.UserService;
import com.cycas.service.WorkTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkTypeService workTypeService;

    @GetMapping("/query")
    public User query(Long id, String monthStr) {
        return userService.selectByPrimaryKey(id, monthStr);
    }

    @GetMapping("/queryByCompanyId")
    public User queryByCompanyId(Long id, String companyId) {
        return userService.queryByCompanyId(id, companyId);
    }

    @GetMapping("/queryUsersByCondition")
    public PageInfo<User> queryUsersByCondition(String name, String companyId, int pageNum, int pageSize) {
        return userService.queryUsersByCondition(name, companyId, pageNum, pageSize);
    }

    @GetMapping("/save")
    public Integer save() {
//        userService.saveUser();
//        workTypeService.batchSaveRoleFunc();
        workTypeService.deleteRoleFunc();
        return 1;
    }

    public static void main(String[] args) {
        List<SyncSysRoleFuncDTO> list = new ArrayList<>();
        SyncSysRoleFuncDTO d1 = new SyncSysRoleFuncDTO();
        d1.settSysRoleId(1);
        SyncSysRoleFuncDTO d2 = new SyncSysRoleFuncDTO();
        d2.settSysRoleId(1);
        SyncSysRoleFuncDTO d3 = new SyncSysRoleFuncDTO();
        d3.settSysRoleId(1);
        SyncSysRoleFuncDTO d4 = new SyncSysRoleFuncDTO();
        d4.settSysRoleId(2);
        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        Set<Integer> set = list.stream().map(SyncSysRoleFuncDTO::gettSysRoleId).collect(Collectors.toSet());
        System.out.println(set);
    }
}
