package com.power.chb.controller;


import com.power.result.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
public class HellowController {


    @RequestMapping(value = "/hello")
    public ResultEntity<String> hello() {

        return ResultEntity.success("年晒得盛撒打算大的",0);
    }


    @RequestMapping(value = "/canshu")
    public String hello( BindingResult bindingResult) {

        StringBuffer stringBuffer = new StringBuffer();
        if(bindingResult.hasErrors()){
            List<ObjectError> list =bindingResult.getAllErrors();
            for (ObjectError objectError:list) {
                stringBuffer.append(objectError.getDefaultMessage());
                stringBuffer.append("---");
            }
        }
        return stringBuffer!=null?stringBuffer.toString():"";



    }



}
