package com.power.chb.ro.sys;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@ApiModel("部门管理")
public class SysDeptRo  implements Serializable {


    private int id;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15, min = 2, message = "部门名称长度需要在2-15个字之间")
    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("部门父ID")
    private int parentId = 0;

    @NotNull(message = "展示顺序不可以为空")
    @ApiModelProperty("显示排序")
    private int seq=0;

    @Length(max = 150, message = "备注的长度需要在150个字以内")
    @ApiModelProperty("部门备注")
    private String remark;


}
