package com.power.chb.service.sys;

import com.power.chb.model.sys.SysDept;
import com.power.chb.ro.sys.SysDeptRo;
import com.power.chb.vo.sys.dept.SysDeptVo;
import com.power.exception.BizException;

import java.util.List;

public interface SysDeptService {

     int savaBean(SysDeptRo ro) throws BizException;

     int updateBean(SysDeptRo ro)throws BizException;

     List<SysDept> sysDeptList(String deptCode);

     SysDeptVo selectOne(Integer id);

}
