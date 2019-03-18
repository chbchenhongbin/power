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
@TableName("sys_acl_module")
public class SysAclModule implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;//权限模块名称

    @TableField("parent_id")
    private Integer parentId; //上级权限模块id

    @TableField("url")
    private String url;//权限模URL

    @TableField("level")
    private String level;//权限模块层级

    @TableField("seq")
    private Integer seq;//权限模块在当前层级下的顺序，由小到大

    @TableField("status") //状态
    private Integer status;

    @TableField("remark") //备注
    private String remark;

    @TableField("operator") //操作者
    private String operator;

    @TableField("operate_ip") //最后一次更新操作者的ip地址
    private String operateIp;

    @TableField("create_time") //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField("update_time") //修改时间
    private Date updateTime;

}