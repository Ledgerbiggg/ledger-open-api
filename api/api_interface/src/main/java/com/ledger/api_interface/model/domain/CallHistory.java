package com.ledger.api_interface.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName call_history
 */
@TableName(value ="call_history")
@Data
public class CallHistory implements Serializable {
    /**
     * 标识
     */
    @TableId
    private String id;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 接口id
     */
    private String insterface_id;

    /**
     * 调用时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime call_time;

    /**
     * 结果的记录
     */
    private String result;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}