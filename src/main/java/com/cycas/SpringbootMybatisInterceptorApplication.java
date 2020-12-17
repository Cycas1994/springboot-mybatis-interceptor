package com.cycas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cycas.dao")
public class SpringbootMybatisInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisInterceptorApplication.class, args);
    }

}
