package com.power.chb.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power.chb.model.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

        int updateUser(SysUser sysUser);
}
