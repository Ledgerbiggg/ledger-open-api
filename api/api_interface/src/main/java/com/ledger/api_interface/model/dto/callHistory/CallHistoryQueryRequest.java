package com.ledger.api_interface.model.dto.callHistory;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CallHistoryQueryRequest {

    /**
     * 调用时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime call_time;

    private String time;

    /**
     * 结果的记录
     */
    private String result;

}
