package com.power.chb.service.sys.iml;

import com.power.chb.mapper.sys.SysUserMapper;
import com.power.chb.model.sys.SysUser;
import com.power.chb.ro.sys.SysUserRo;
import com.power.chb.service.sys.SysUserService;
import com.power.cutils.BeanValidator;
import com.power.cutils.MD5Util;
import com.power.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public int savaBean(SysUserRo ro) throws BizException {

        BeanValidator.check(ro);
        if(checkTelephoneExist(ro.getTelephone())) {
            throw new BizException("电话已被占用");
        }
        if(checkEmailExist(ro.getMail())) {
            throw new BizException("邮箱已被占用");
        }
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(ro, sysUser);
        sysUser.setPassword(MD5Util.encrypt(sysUser.getPassword()));
        sysUser.setOperator("1");//操作者
        sysUser.setOperateIp("1");//IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())
        sysUser.setCreateTime(new Date());
        // TODO: sendEmail
       int row = sysUserMapper.insert(sysUser);
       return row;
    }

    @Override
    public int updateUser(SysUserRo ro) throws BizException {
        BeanValidator.check(ro);
        if(checkTelephoneExist(ro.getTelephone())) {
            throw new BizException("电话已被占用");
        }
        if(checkEmailExist(ro.getMail())) {
            throw new BizException("邮箱已被占用");
        }
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(ro, sysUser);
        sysUser.setPassword(MD5Util.encrypt(sysUser.getPassword()));
        sysUser.setOperator("1");//操作者
        sysUser.setOperateIp("1");//IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())
        sysUser.setUpdateTime(new Date());
        // TODO: sendEmail
        int row = sysUserMapper.updateUser(sysUser);

        return row;
    }




    /**
     *  查询电话是否被占用
     * @param telephone
     * @param userId
     * @return
     */
    public boolean checkTelephoneExist(String telephone) {
       return  false;
    }

    /**
     * 查询邮箱是否被占用
     * @param mail
     * @param userId
     * @return
     */
    public boolean checkEmailExist(String mail) {
        return  false;
    }


}
