package com.power.chb.service.sys.iml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.base.Preconditions;
import com.power.chb.mapper.sys.SysAclModuleMapper;
import com.power.chb.model.sys.SysAclModule;
import com.power.chb.ro.sys.SysAclModuleRo;
import com.power.chb.service.sys.SysAclModuleService;
import com.power.cutils.BeanValidator;
import com.power.cutils.LevelUtil;
import com.power.exception.ParamException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {


    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Override
    public int saveBean(SysAclModuleRo ro) {
        BeanValidator.check(ro);
        if(checkExist(ro.getParentId(), ro.getName(), ro.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        SysAclModule sysAclModule=new SysAclModule();
        BeanUtils.copyProperties(ro,sysAclModule);
        sysAclModule.setOperator("");
        sysAclModule.setOperateIp("");
        sysAclModule.setCreateTime(new Date());
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(ro.getParentId()), ro.getParentId()));
        //TODO 加入日志
        return sysAclModuleMapper.insert(sysAclModule);
    }

    @Override
    public int updateBean(SysAclModuleRo ro) {
        BeanValidator.check(ro);
        if(checkExist(ro.getParentId(), ro.getName(), ro.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        SysAclModule before = sysAclModuleMapper.selectById(ro.getId());

        //断言 相当于if
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        SysAclModule sysAclModule=new SysAclModule();
        BeanUtils.copyProperties(ro,sysAclModule);
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(ro.getParentId()), ro.getParentId()));
        sysAclModule.setOperator("");
        sysAclModule.setOperateIp("");
        sysAclModule.setUpdateTime(new Date());

        updateWithChild(before, sysAclModule);


        return 0;
    }

    @Override
    public SysAclModule  selectOneAclModule(Integer id) {
        return sysAclModuleMapper.selectById(id);
    }

    //修改父类下面的所有子类
    @Transactional
    public void updateWithChild(SysAclModule before, SysAclModule after)
    {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
         if(!newLevelPrefix.equals(oldLevelPrefix))
         {
             QueryWrapper qw=new QueryWrapper();
             qw.likeRight("level",oldLevelPrefix+".");
             List<SysAclModule> sysAclModuleList = sysAclModuleMapper.selectList(qw);//得到当前要修改的权限，下面的所有子权限，修改其层级
             if (CollectionUtils.isNotEmpty(sysAclModuleList)) {

                 for(SysAclModule  sysAclModule : sysAclModuleList)
                 {
                     //修改子权限  层级  重新赋值
                     String level=sysAclModule.getLevel();
                     if(level.indexOf(oldLevelPrefix) == 0) //返回指定字符在字符串中第一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
                     {
                         level = newLevelPrefix + level.substring(oldLevelPrefix.length());//返回从起始位置（beginIndex）至字符串末尾的字符串
                         sysAclModule.setLevel(level);
                     }

                     Map<String,Object> map=new HashMap<>();
                     map.put("level",sysAclModule.getLevel());
                     map.put("id",sysAclModule.getId());
                     sysAclModuleMapper.batchUpdateLevel(map);
                 }

             }


         }


    }


    private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
        return  false;
    }


    /**
     * 权限模块层级
     * @param aclModuleId
     * @return
     */
    private String getLevel(Integer aclModuleId) {

        SysAclModule aclModule = sysAclModuleMapper.selectById(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }

}
