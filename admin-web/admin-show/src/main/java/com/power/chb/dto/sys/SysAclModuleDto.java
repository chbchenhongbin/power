package com.power.chb.dto.sys;

import com.google.common.collect.Lists;
import com.power.chb.model.sys.SysAclModule;
import com.power.chb.model.sys.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class SysAclModuleDto extends SysAclModule {

        private List<SysAclModuleDto> children= Lists.newArrayList();  //

        public  static SysAclModuleDto copyAclModule(SysAclModule sysDept)
        {
            SysAclModuleDto sysDeptTreeDto=new SysAclModuleDto();
            BeanUtils.copyProperties(sysDept,sysDeptTreeDto);
            return sysDeptTreeDto;
        }

}
