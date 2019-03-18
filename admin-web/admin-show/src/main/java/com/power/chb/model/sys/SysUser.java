package com.power.chb.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_name") //用户名称
    private String userName;

    @TableField("telephone")
    private String telephone;

    @TableField("mail")
    private String mail;

    @TableField("password")
    private String password;

    @TableField("dept_id")
    private Integer deptId; //用户所在部门的id

    @TableField("status")
    private Integer status;

    @TableField("remark")
    private String remark;

    @TableField("operator")
    private String operator;//操作者

    @TableField("operate_ip")
    private String operateIp;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;



}