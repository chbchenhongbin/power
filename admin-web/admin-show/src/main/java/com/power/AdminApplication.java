package com.power;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.power.chb.mapper.sys")
@ComponentScan(basePackages={"com.power.*"})
@EnableSwagger2
public class AdminApplication {

    //http://localhost:8888/doc.html  swagger
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }


}

