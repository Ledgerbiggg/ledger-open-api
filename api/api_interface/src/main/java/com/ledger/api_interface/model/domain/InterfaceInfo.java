package com.ledger.api_interface.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储接口信息的表
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 接口ID
     */
    @TableId
    private String id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 接口URL
     */
    private String url;

    /**
     * 接口状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer is_delete;

    /**
     * 最后更新时间
     */
    private Date update_time;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 接口方法
     */
    private String method;

    /**
     * 接口图像地址

     */
    private String img_url;

    /**
     * 调用次数
     */
    private Integer call_count;

    /**
     * 返回类型
     */
    private String resp_type;

    /**
     * 消费积分
     */
    private Integer consume;

    private String example;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}