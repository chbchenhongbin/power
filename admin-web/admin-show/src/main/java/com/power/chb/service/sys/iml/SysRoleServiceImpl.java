package com.power.chb.service.sys.iml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.power.chb.mapper.sys.SysRoleMapper;
import com.power.chb.model.sys.SysRole;
import com.power.chb.ro.sys.SysRoleRo;
import com.power.chb.service.sys.SysRoleService;
import com.power.cutils.BeanValidator;
import com.power.exception.ParamException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public int saveBean(SysRoleRo ro) {
        BeanValidator.check(ro);

        if(checkExist(ro.getName())>0)
        {
            throw new ParamException("角色名不能重复");
        }
        SysRole sysRole=new SysRole();
        BeanUtils.copyProperties(ro,sysRole);
        sysRole.setOperateTime(new Date());

        return sysRoleMapper.insert(sysRole);
    }

    @Override
    public int updateBean(SysRoleRo ro) {
        BeanValidator.check(ro);
        if(checkExist(ro.getName())>0){
            throw new ParamException("角色名称已经存在");
        }

        SysRole before = sysRoleMapper.selectById(ro.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");
        BeanUtils.copyProperties(ro,before);

        return sysRoleMapper.updateById(before);
    }

    public int  checkExist(String name)
    {
        //角色名不能重复
        Map<String,Object> map= Maps.newHashMap();
        map.put("name",name);
        List<SysRole> roleList=sysRoleMapper.selectByMap(map);
        return roleList.size();
    }
}
