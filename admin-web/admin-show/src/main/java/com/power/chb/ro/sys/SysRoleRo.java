package com.power.chb.ro.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@ApiModel("角色管理")
public class SysRoleRo implements Serializable {


    @ApiModelProperty("角色Id,修改时候用到")
    private Integer id;

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    @ApiModelProperty("角色姓名")
    private String name;

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    @ApiModelProperty("角色类型")
    private Integer type = 1;

    @NotNull(message = "角色状态不可以为空")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    @ApiModelProperty("角色状态")
    private Integer status;

    @Length(min = 0, max = 200, message = "角色备注长度需要在200个字符以内")
    @ApiModelProperty("角色备注")
    private String remark;

}
