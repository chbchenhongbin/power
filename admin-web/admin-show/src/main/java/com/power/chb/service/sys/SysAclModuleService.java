package com.power.chb.service.sys;

import com.power.chb.model.sys.SysAclModule;
import com.power.chb.ro.sys.SysAclModuleRo;

public interface SysAclModuleService {

        int  saveBean(SysAclModuleRo ro);

        int updateBean(SysAclModuleRo ro);

        SysAclModule selectOneAclModule(Integer id);

}
