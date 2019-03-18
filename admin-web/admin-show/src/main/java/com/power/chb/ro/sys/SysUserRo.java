package com.power.chb.ro.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@ApiModel("用户添加")
public class SysUserRo implements Serializable {


    @ApiModelProperty("用户id,修改时候用到")
    private Integer id;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户电话")
    private String telephone;

    @ApiModelProperty("用户邮箱")
    private String mail;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户所在部门的id")
    private Integer deptId;

    @ApiModelProperty("用户状态")
    private Integer status;

    @ApiModelProperty("用户备注")
    private String remark;

}
