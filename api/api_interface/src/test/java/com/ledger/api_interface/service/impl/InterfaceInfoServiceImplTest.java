package com.ledger.api_interface.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ledger.api_common.response.Result;
import com.ledger.api_interface.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class InterfaceInfoServiceImplTest {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Test
    void call() {
        HashMap<String, Object> map = new HashMap<>();


        map.put("phone","18248625125");
        Result<JSONObject> get =
                interfaceInfoService.call("https://v.api.aa1.cn/api/phone/guishu-api.php", "GET", map);
        log.info(get.getData().toString());

    }
}