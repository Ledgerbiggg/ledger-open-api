package com.ledger.api_common.model.query;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageQuery {
    private int pageNum=1;
    private int pageSize=10;
    private String total;

}
