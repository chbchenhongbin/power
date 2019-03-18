package com.power.chb.service.sys;

import com.power.chb.dto.sys.SysAclModuleDto;
import com.power.chb.dto.sys.SysDeptTreeDto;

import java.util.List;

public interface SysTreeService {

    List<SysDeptTreeDto> listDept();

    List<SysAclModuleDto> listAclModule();



}
