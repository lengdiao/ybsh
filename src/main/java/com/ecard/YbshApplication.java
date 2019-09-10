package com.ecard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.ecard.dao")
@EnableTransactionManagement
public class YbshApplication {

    public static void main(String[] args) {
        SpringApplication.run(YbshApplication.class, args);
    }


}
