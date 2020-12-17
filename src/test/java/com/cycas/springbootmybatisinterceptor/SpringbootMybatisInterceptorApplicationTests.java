package com.cycas.springbootmybatisinterceptor;

import com.cycas.service.WorkTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootMybatisInterceptorApplicationTests {

    @Autowired
    private WorkTypeService workTypeService;

    @Test
    void batchSaveRoleFunc() {
        workTypeService.insertForeach();
    }

}
