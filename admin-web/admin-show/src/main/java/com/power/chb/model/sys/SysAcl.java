package com.power.chb.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@TableName("sys_acl")
public class SysAcl implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("code") //权限码
    private String code;

    @TableField("name") //权限名称
    private String name;

    @TableField("acl_module_id")
    private Integer aclModuleId; //权限所在的权限模块id

    @TableField("url")
    private String url; //请求的url, 可以填正则表达式

    //类型，1：菜单，2：按钮，3：其他
    @TableField("type")
    private Integer type;

    @TableField("status") //状态，1：正常，0：冻结
    private Integer status;

    @TableField("seq")
    private Integer seq; //权限在当前模块下的顺序，由小到大

    @TableField("remark")
    private String remark; //备注

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;  //创建时间

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;  //修改时间


    @TableField("operator") //操作者
    private String operator;

    @TableField("operate_ip") //最后一次更新操作者的ip地址
    private String operateIp;




}