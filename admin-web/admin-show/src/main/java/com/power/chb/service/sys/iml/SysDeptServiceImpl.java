package com.power.chb.service.sys.iml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.base.Preconditions;
import com.power.chb.mapper.sys.SysDeptMapper;
import com.power.chb.model.sys.SysDept;
import com.power.chb.ro.sys.SysDeptRo;
import com.power.chb.service.sys.SysDeptService;
import com.power.chb.vo.sys.dept.SysDeptVo;
import com.power.cutils.LevelUtil;
import com.power.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public int savaBean(SysDeptRo ro) throws  BizException {
        //先判断  同一个部门 先 不能又相同的部门名称
        if(checkExist(ro.getParentId(), ro.getName(), ro.getId())) {
            throw new BizException("同一层级下存在相同名称的部门");
        }
        SysDept sysDept =new SysDept();
        BeanUtils.copyProperties(ro, sysDept);//copy 页面传来的参数
        String level=LevelUtil.calculateLevel(getLevel(ro.getParentId()),ro.getParentId());
        sysDept.setLevel(level);
        sysDept.setOperator("admin");
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setCreateTime(new Date());
        sysDept.setUpdateTime(new Date());

        int row=sysDeptMapper.insert(sysDept);
        return row;
    }


    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {

        QueryWrapper qw=new QueryWrapper();
        qw.eq("name",deptName);
        qw.eq("parent_id",parentId);
        if(deptId!=0)
        {
            qw.ne("id",deptId);
        }
        if (sysDeptMapper.selectCount(qw)>0)
        {
            return true;
        }
        return false;
    }

    //得到 部门层级
    private String getLevel(Integer deptId) {

        QueryWrapper qw=new QueryWrapper();
        qw.eq("id",deptId);
        SysDept dept = sysDeptMapper.selectOne(qw);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }

    @Override
    public int updateBean(SysDeptRo ro) throws BizException {
        //先判断  同一个部门 先 不能又相同的部门名称
        if(checkExist(ro.getParentId(), ro.getName(), ro.getId())) {
            throw new BizException("同一层级下存在相同名称的部门");
        }

        QueryWrapper qw=new QueryWrapper();
        qw.eq("id",ro.getId());
        SysDept before = sysDeptMapper.selectOne(qw);
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        SysDept after =new SysDept();
        BeanUtils.copyProperties(ro, after);//copy 页面传来的参数
        after.setLevel(LevelUtil.calculateLevel(getLevel(ro.getParentId()), ro.getParentId()));
        after.setOperator("admin");
        after.setOperateIp("127.0.0.1");
        after.setCreateTime(before.getCreateTime());
        after.setUpdateTime(new Date());

        //批量修改 子部
        return  updateWithChild(before, after);
    }



    @Transactional
    public int updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());//返回从起始位置（beginIndex）至字符串末尾的字符串
                        dept.setLevel(level);
                    }
                    Map<String,Object> map=new HashMap<>();
                    map.put("level",dept.getLevel());
                    map.put("id",dept.getId());
                    sysDeptMapper.batchUpdateLevel(map);
                }

            }
        }
      return  sysDeptMapper.updateById(after);
    }

    @Override
    public List<SysDept> sysDeptList(String deptCode) {
        QueryWrapper qw=new QueryWrapper();
        //qw.eq("id",deptCode);
        qw.orderByAsc("seq");
        return  sysDeptMapper.selectList(qw);
    }

    @Override
    public SysDeptVo selectOne(Integer id) {
        if(id==null)
        {
            return null;
        }
        QueryWrapper qw=new QueryWrapper();
        qw.eq("id",id);
        SysDept sysDept=sysDeptMapper.selectOne(qw);
        SysDeptVo vo=new SysDeptVo();
        BeanUtils.copyProperties(sysDept,vo);

        QueryWrapper pqw=new QueryWrapper();
        pqw.eq("id",sysDept.getParentId());
        SysDept parentDept=sysDeptMapper.selectOne(pqw);
        if(parentDept==null)
        {
            vo.setParentName(vo.getName());
        }else
            {
                vo.setParentName(parentDept.getName());
            }


        return vo;
    }


}
