package com.power.chb.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor  //生成一个无参数的构造方法
@AllArgsConstructor //会生成一个包含所有变量
@ToString
@Builder
@TableName("sys_role")
public class SysRole  implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id; //角色id

    @TableField("name") //角色名称
    private String name;

    @TableField("type") //角色的类型，1：管理员角色，2：其他
    private Integer type;

    @TableField("status")//状态，1：可用，0：冻结
    private Integer status;

    @TableField("remark")
    private String remark;

    @TableField("operator")
    private String operator;

    @TableField("operate_time")
    private Date operateTime;//最后一次更新的时间

    @TableField("operate_ip")
    private String operateIp;//最后一次更新者的ip地址


}