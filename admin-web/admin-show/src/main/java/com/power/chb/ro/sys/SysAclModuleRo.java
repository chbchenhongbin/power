package com.power.chb.ro.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("权限管理")
public class SysAclModuleRo {

    @ApiModelProperty("权限id,修改时候用到")
    private Integer id;

    @ApiModelProperty("权限模块名称")
    private String name;

    @ApiModelProperty("权限父级Id，默认为0")
    private Integer parentId = 0;

    @ApiModelProperty("url")
    private String url;//权限模URL

    @ApiModelProperty("权限模块展示顺序")
    private Integer seq;

    @ApiModelProperty("权限模块状态")
    private Integer status;

    @ApiModelProperty("权限模块备注")
    private String remark;
}
