package com.power.test;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {


//        Map<String,String> map= BeanValidator.validate(aa);
//        if(map!=null && map.entrySet().size()>0)
//        {
//            for(Map.Entry<String,String> entry : map.entrySet())
//            {
//                log.info("{}--->{}",entry.getKey(),entry.getValue());
//            }
//        }


        Map<String,Object> map=new HashMap<>();
        map.put("a","1");
        map.put("a","2");
        System.out.println(map);


    }
}
