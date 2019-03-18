package com.power.chb.service.sys.iml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.power.chb.dto.sys.SysAclModuleDto;
import com.power.chb.dto.sys.SysDeptTreeDto;
import com.power.chb.mapper.sys.SysAclModuleMapper;
import com.power.chb.mapper.sys.SysDeptMapper;
import com.power.chb.model.sys.SysAclModule;
import com.power.chb.model.sys.SysDept;
import com.power.chb.service.sys.SysTreeService;
import com.power.cutils.LevelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Override
    public List<SysDeptTreeDto> listDept()
    {

        QueryWrapper qw=new QueryWrapper();
        List<SysDept> deptList=sysDeptMapper.selectList(qw);

        List<SysDeptTreeDto> dtoList= Lists.newArrayList();

        for(SysDept sysDept : deptList)
        {
            SysDeptTreeDto dto=new SysDeptTreeDto();
            BeanUtils.copyProperties(sysDept,dto);
            dtoList.add(dto);
        }

        return deptTreeDtoList(dtoList);
    }

    //部门 组装tree
    public List<SysDeptTreeDto> deptTreeDtoList(List<SysDeptTreeDto> dtoList)
    {
        //判断当前的 list 是否又值
        if(CollectionUtils.isEmpty(dtoList))
        {
            return Lists.newArrayList();
        }

        Multimap<String,SysDeptTreeDto> multimap= ArrayListMultimap.create();
        List<SysDeptTreeDto> rootList=Lists.newArrayList();

        for(SysDeptTreeDto sysDeptTreeDto : dtoList )
        {
            //相当于 Map<String, List<Object>>
            multimap.put(sysDeptTreeDto.getLevel(),sysDeptTreeDto);
            if (LevelUtil.ROOT.equals(sysDeptTreeDto.getLevel())) {
                rootList.add(sysDeptTreeDto);
            }
        }

        // 按照seq从小到大排序
        Collections.sort(rootList, new Comparator<SysDeptTreeDto>() {
            public int compare(SysDeptTreeDto o1, SysDeptTreeDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });

        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, multimap);

        return rootList;
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1
    // level:0.2
    public void transformDeptTree(List<SysDeptTreeDto> deptLevelList, String level, Multimap<String, SysDeptTreeDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {

            // 遍历该层的每个元素
            SysDeptTreeDto deptLevelDto = deptLevelList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            // 处理下一层
            List<SysDeptTreeDto> tempDeptList = (List<SysDeptTreeDto>) levelDeptMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);
                // 设置下一层部门
                deptLevelDto.setChildren(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    public Comparator<SysDeptTreeDto> deptSeqComparator = new Comparator<SysDeptTreeDto>() {
        public int compare(SysDeptTreeDto o1, SysDeptTreeDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    /**
     * 权限集合
     * @return
     */
    @Override
    public List<SysAclModuleDto> listAclModule() {
        QueryWrapper qw=new QueryWrapper();
        List<SysAclModule> sysAclModuleList=sysAclModuleMapper.selectList(qw);
        List<SysAclModuleDto> sysAclModuleDtoList=Lists.newArrayList();
        for(SysAclModule sysAclModule : sysAclModuleList)
        {
            sysAclModuleDtoList.add(SysAclModuleDto.copyAclModule(sysAclModule));
        }
        //查询出数据 然后封装
        return aclModuleListToTree(sysAclModuleDtoList);
    }

    public List<SysAclModuleDto> aclModuleListToTree(List<SysAclModuleDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        //  ArrayListMultimap 底层可以认为是 Map<K, Collection<V>>,且key可以重复.  一键多值
        Multimap<String, SysAclModuleDto> levelAclModuleMap = ArrayListMultimap.create();
        List<SysAclModuleDto> rootList = Lists.newArrayList();

        for (SysAclModuleDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }

        Collections.sort(rootList, aclModuleSeqComparator);
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    //自定义排序
    public Comparator<SysAclModuleDto> aclModuleSeqComparator = new Comparator<SysAclModuleDto>() {
        public int compare(SysAclModuleDto o1, SysAclModuleDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    //利用：level  来得到值 拼接 只要 key 没有了值 就退出递归
    public void transformAclModuleTree(List<SysAclModuleDto> dtoList, String level, Multimap<String, SysAclModuleDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            SysAclModuleDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<SysAclModuleDto> tempList = (List<SysAclModuleDto>) levelAclModuleMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(tempList)) {
                Collections.sort(tempList, aclModuleSeqComparator);
                dto.setChildren(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }


}
