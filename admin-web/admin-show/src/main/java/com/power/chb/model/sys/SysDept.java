package com.power.chb.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_dept")
@NoArgsConstructor  //生成一个无参数的构造方法
@AllArgsConstructor //会生成一个包含所有变量
@ToString
@Builder
public class SysDept implements Serializable {

      @TableId(value = "id", type = IdType.AUTO)
      private  int  id;

      @TableField("name")
      private String name;  //部门名称

      @TableField("parent_id")
      private int parentId;  //上级部门id 默认为0

      @TableField("level")
      private String level;  //部门层级

      @TableField("seq")
      private int seq;  //部门在当前层级下的顺序，由小到大

      @TableField("remark")
      private String remark;  //备注

      @TableField("create_time")
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
      private Date createTime;  //创建时间

      @TableField("update_time")
      private Date updateTime;  //修改时间

      @TableField("operator")
      private String operator;  //操作者

      @TableField("operate_ip")
      private String operateIp;  //最后一次更新操作者的ip地址

      @TableField("is_del")
      private int isDel;  //是否删除


}
