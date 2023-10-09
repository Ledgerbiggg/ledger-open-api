package com.ledger.api_interface.model.vo.InterfaceInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ledger.api_interface.model.domain.RequestParameters;
import com.ledger.api_interface.model.domain.ResponseParameters;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class InterfaceInfoWithParams {
    /**
     * 接口ID
     */
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
     * 接口方法
     */
    private String method;


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

    List<RequestParameters> requestParametersList;


    List<ResponseParameters> responseParametersList;




}
