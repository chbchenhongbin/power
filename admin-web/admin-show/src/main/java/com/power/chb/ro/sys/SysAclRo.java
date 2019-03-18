package com.power.chb.ro.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
@ApiModel("权限点管理")
public class SysAclRo implements Serializable {


    @ApiModelProperty("权限点Id,修改时候用到")
    private Integer id;

    @NotBlank(message = "权限点名称不可以为空")
    @Length(min = 2, max = 20, message = "权限点名称长度需要在2-20个字之间")
    @ApiModelProperty("权限点名称")
    private String name;

    @ApiModelProperty("必须指定权限模块")
    private Integer aclModuleId;

    @ApiModelProperty("权限点URL长度")
    private String url;

    @ApiModelProperty("必须指定权限点的类型   类型，1：菜单，2：按钮，3：其他")
    private Integer type;

    @ApiModelProperty("权限点状态")
    private Integer status;

    @ApiModelProperty("必须指定权限点的状态")
    private Integer seq;

    @ApiModelProperty("备注")
    private String remark;


}
