package com.ledger.api_common.test;

import com.ledger.api_common.annotation.ledgerApi;
import lombok.Data;


@Data
@ledgerApi(
        url="https://test.harumoe.cn/api/other/hot",
        AccessKey="bdb01e698bf348c4930f6881c508d54d",
        SecretKey="2f783ee0c1c3452b9e0945edc2ef58b6")
public class HotSou {
    private boolean cache;
    private String list;
    private String lang;
}
