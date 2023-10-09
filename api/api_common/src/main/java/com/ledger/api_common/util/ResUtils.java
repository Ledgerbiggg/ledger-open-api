package com.ledger.api_common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.StringJoiner;

public class ResUtils {
    private static final RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
    }

    public static Object postDataForCommon(String url, Map<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Charset", "UTF-8");
        HttpEntity<String> requestEntity = new HttpEntity<>(JSONUtil.toJsonStr(map), headers);
        ResponseEntity<String> res = restTemplate.postForEntity(url, requestEntity, String.class);
        try {
            return JSON.parseObject(res.getBody(), JSONObject.class);
        } catch (JSONException e) {
            return JSON.parseObject(res.getBody(), JSONArray.class);
        }
    }

    public static Object getDataForCommon(String url, Map<String, Object> map) {
        StringJoiner sj = new StringJoiner("&", "?", "");
        map.forEach((k, v) -> {
            if (!k.equals("url")) {
                sj.add(k + "=" + v);
            }
        });
        ResponseEntity<String> res = restTemplate.getForEntity(url + sj, String.class);
        String body = res.getBody();
        JSON.toJSONString(body);
        try {
            return JSON.parseObject(res.getBody(), JSONObject.class);
        } catch (JSONException e) {
            return JSON.parseObject(res.getBody(), JSONArray.class);
        }


    }
}
