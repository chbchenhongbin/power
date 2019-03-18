package com.power.chb.dto.sys;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.power.chb.model.sys.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class SysDeptTreeDto extends SysDept {

        private List<SysDeptTreeDto> children= Lists.newArrayList();

        public  static  SysDeptTreeDto copyDept(SysDept sysDept)
        {
            SysDeptTreeDto sysDeptTreeDto=new SysDeptTreeDto();
            BeanUtils.copyProperties(sysDept,sysDeptTreeDto);
            return sysDeptTreeDto;
        }

}
