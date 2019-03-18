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
@TableName("sys_role_user")
public class SysRoleUser implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("role_id")
    private Integer roleId;

    @TableField("user_id")
    private Integer userId;

    @TableField("operator")
    private String operator;

    @TableField("operate_time")
    private Date operateTime;

    @TableField("operate_ip")
    private String operateIp;


}