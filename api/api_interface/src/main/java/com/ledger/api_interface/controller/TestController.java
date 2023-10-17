package com.ledger.api_interface.controller;

import com.ledger.api_common.enums.ContentTypeEnum;
import com.ledger.api_common.util.HttpUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Controller
@RequestMapping("/interfaceInfo")
public class TestController {

    @GetMapping("/test")
    @ApiOperation("测试") // 添加 API 操作说明
    public void test(HttpServletResponse httpServletResponse) {

    }
}
