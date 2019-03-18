package com.power.chb.service.sys;

import com.power.chb.ro.sys.SysUserRo;
import com.power.exception.BizException;

public interface SysUserService {

    int savaBean(SysUserRo ro) throws BizException;

    int updateUser(SysUserRo sysUser)  throws BizException;
}
