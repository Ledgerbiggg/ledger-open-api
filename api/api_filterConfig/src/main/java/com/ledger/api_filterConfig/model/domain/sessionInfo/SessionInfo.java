package com.ledger.api_filterConfig.model.domain.sessionInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @TableName session_info
 */
@Data
@TableName(value ="session_info")
public class SessionInfo implements Serializable {

    @TableId
    private String id;


    private String user_id;


    private String user_icon;


    private String username;


    private String message;


    private String custom_service;


    private LocalDateTime dt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}