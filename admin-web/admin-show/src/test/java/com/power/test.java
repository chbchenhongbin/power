package com.power;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.power.chb.mapper.sys.SysDeptMapper;
import com.power.chb.model.sys.SysAclModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Test
    public void contextLoads() {


       // System.out.println( sysDeptMapper.getChildDeptListByLevel("0.17"));
        Map<String,Object> map=new HashMap<>();
        map.put("parent_id",31);
        QueryWrapper qw=new QueryWrapper();

        qw.likeRight("level","0.17");
        System.out.println(sysDeptMapper.selectList(qw));


    }





}
