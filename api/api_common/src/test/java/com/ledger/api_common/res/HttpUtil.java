package com.ledger.api_common.res;


import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class HttpUtil {
    private static final RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
    }

    /**
     * get请求(通用)
     *
     * @param url          地址
     * @param params       参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static Object get(String url, Map<String, Object> params, HashMap<String,String> reqHeaderMap) {
        StringJoiner sj = new StringJoiner("&", "?", "");
        if (params != null) {
            params.forEach((k, v) -> {
                if (!"url".equals(k)) {
                    sj.add(k + "=" + v);
                }
            });
        }
        //请求头
        HttpHeaders headers = new HttpHeaders();
        if (reqHeaderMap != null) {
            reqHeaderMap.forEach(headers::set);
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> res = restTemplate.exchange(
                url + sj,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        return res.getBody();
    }

    /**
     * post请求(通用)
     *
     * @param url          地址
     * @param resBody      参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static Object post(String url, Map<String, Object> resBody, HashMap<String,String> reqHeaderMap) {
        //请求头
        HttpHeaders headers = new HttpHeaders();
        if (reqHeaderMap != null) {
            reqHeaderMap.forEach(headers::set);
        }
        //请求体
        String requestBody = JSON.toJSONString(resBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> res = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return res.getBody();
    }


    public static void getAndSend(String url,
                                  Map<String, Object> params,
                                  HashMap<String,String> reqHeaderMap,
                                  HttpServletResponse httpServletResponse,
                                  ContentTypeEnum contentTypeEnum) {
        Object data = get(url, params, reqHeaderMap);
        httpServletResponse.setContentType(contentTypeEnum.getContentType());
        write(httpServletResponse, data);
    }

    public static void postAndSend(String url,
                                   Map<String, Object> params,
                                   HashMap<String,String> reqHeaderMap,
                                   HttpServletResponse httpServletResponse,
                                   ContentTypeEnum contentTypeEnum) {
        Object data = post(url, params, reqHeaderMap);
        httpServletResponse.setContentType(contentTypeEnum.getContentType());
        write(httpServletResponse, data);
    }

    private static void write(HttpServletResponse httpServletResponse, Object data) {
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.println(data);
            writer.flush();
        } catch (IOException e) {
            writeWrong(httpServletResponse, writer, e);
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private static void writeWrong(HttpServletResponse httpServletResponse, PrintWriter writer, Exception e) {
        if(writer==null) {
            return;
        }
        writer.println("出错" + e.getMessage());
        httpServletResponse.setContentType(ContentTypeEnum.TEXT_PLAIN.getContentType());
    }


}
