package com.power.chb.vo.sys.dept;


import com.power.chb.model.sys.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysDeptVo extends SysDept {

    private  String parentName;  // 父部门名称

}
