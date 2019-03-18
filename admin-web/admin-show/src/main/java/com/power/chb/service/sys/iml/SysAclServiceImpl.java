package com.power.chb.service.sys.iml;

import com.google.common.base.Preconditions;
import com.power.chb.mapper.sys.SysAclMapper;
import com.power.chb.model.sys.SysAcl;
import com.power.chb.ro.sys.SysAclRo;
import com.power.chb.service.sys.SysAclService;
import com.power.cutils.BeanValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class SysAclServiceImpl implements SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Override
    public int saveBean(SysAclRo ro) {
        BeanValidator.check(ro);
        SysAcl sysAcl=new SysAcl();
        BeanUtils.copyProperties(ro,sysAcl);
        sysAcl.setOperator("");
        sysAcl.setOperateIp("");
        sysAcl.setCreateTime(new Date());
        sysAcl.setCode(generateCode());
        return sysAclMapper.insert(sysAcl);
    }

    @Override
    public int updateBean(SysAclRo ro) {
        BeanValidator.check(ro);
        SysAcl oldSysAcl=sysAclMapper.selectById(ro.getId());
        //断言 相当于if
        Preconditions.checkNotNull(oldSysAcl, "待更新的权限点模块不存在");

        BeanUtils.copyProperties(ro,oldSysAcl);
        oldSysAcl.setUpdateTime(new Date());
        oldSysAcl.setOperator("");
        oldSysAcl.setOperateIp("");

        return sysAclMapper.updateById(oldSysAcl);
    }

    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }

}
