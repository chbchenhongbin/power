package com.power.chb.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power.chb.model.sys.SysAclModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


@Mapper
public interface SysAclModuleMapper extends BaseMapper<SysAclModule> {

   int  batchUpdateLevel(Map<String,Object> map);

}
