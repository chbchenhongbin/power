package com.power.chb.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power.chb.model.sys.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

        List<SysDept> getChildDeptListByLevel(@Param("level") String level);

        int  batchUpdateLevel(Map<String,Object> map);
}
