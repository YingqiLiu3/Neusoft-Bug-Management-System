package com.dlut.bugtestmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dlut.bugtestmanage.mapper")
public class BugTestManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BugTestManageApplication.class, args);
    }

}
