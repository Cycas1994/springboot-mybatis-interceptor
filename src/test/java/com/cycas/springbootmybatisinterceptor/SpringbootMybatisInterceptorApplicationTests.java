package com.cycas.springbootmybatisinterceptor;

import com.cycas.service.UserService;
import com.cycas.service.WorkTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootMybatisInterceptorApplicationTests {

    @Autowired
    private WorkTypeService workTypeService;
    @Autowired
    private UserService userService;

    @Test
    void batchSaveRoleFunc() {
        for (long i = 1; i < 1000; i++) {
            userService.saveUser(i);
        }
    }

}
