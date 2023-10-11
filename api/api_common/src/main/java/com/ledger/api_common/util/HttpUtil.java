package com.ledger.api_common.util;


import com.alibaba.fastjson.JSON;
import com.ledger.api_common.enums.ContentTypeEnum;
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
    public static String get(String url, Map<String, Object> params, HashMap<String, String> reqHeaderMap) {
        return getUtil(url, params, reqHeaderMap, String.class);
    }

    /**
     * post请求(通用)
     *
     * @param url          地址
     * @param resBody      参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static String post(String url, Map<String, Object> resBody, HashMap<String, String> reqHeaderMap) {
        return postUtil(url, resBody, reqHeaderMap, String.class);
    }

    /**
     * get请求(字节数组)
     *
     * @param url          地址
     * @param params       参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static byte[] getByteArr(String url, Map<String, Object> params, HashMap<String, String> reqHeaderMap) {
      return getUtil(url, params, reqHeaderMap, byte[].class);
    }

    /**
     * post请求(字节数组)
     *
     * @param url          地址
     * @param resBody      参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static byte[] postByteArr(String url, Map<String, Object> resBody, HashMap<String, String> reqHeaderMap) {
       return postUtil(url, resBody, reqHeaderMap, byte[].class);
    }

    /**
     * get将文件转换为json字符串
     *
     * @param url          url
     * @param params       路径参数
     * @param reqHeaderMap 请求头
     * @return 返回json
     */
    public static JSON getJson(String url, Map<String, Object> params, HashMap<String, String> reqHeaderMap) {
        Object data = get(url, params, reqHeaderMap);
        return JSON.parseObject((String) data, JSON.class);
    }

    /**
     * post将文件转换为json字符串
     *
     * @param url          url
     * @param resBody      请求体
     * @param reqHeaderMap 请求头
     * @return 返回json
     */
    public static JSON postJson(String url, Map<String, Object> resBody, HashMap<String, String> reqHeaderMap) {
        Object data = post(url, resBody, reqHeaderMap);
        return JSON.parseObject((String) data, JSON.class);
    }


    /**
     * get直接返回响应(根据数据的类型)
     *
     * @param url                 url
     * @param params              路径参数
     * @param reqHeaderMap        请求头
     * @param httpServletResponse 响应
     * @param contentTypeEnum     响应类型
     */
    public static void getAndSend(String url,
                                  Map<String, Object> params,
                                  HashMap<String, String> reqHeaderMap,
                                  HttpServletResponse httpServletResponse,
                                  ContentTypeEnum contentTypeEnum) {
        Object data = get(url, params, reqHeaderMap);
        httpServletResponse.setContentType(contentTypeEnum.getContentType());
        try {
            write(httpServletResponse, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * post直接返回响应(根据数据的类型)
     *
     * @param url                 url
     * @param resBody             请求体
     * @param reqHeaderMap        请求头
     * @param httpServletResponse 响应
     * @param contentTypeEnum     响应类型
     */
    public static void postAndSend(String url,
                                   Map<String, Object> resBody,
                                   HashMap<String, String> reqHeaderMap,
                                   HttpServletResponse httpServletResponse,
                                   ContentTypeEnum contentTypeEnum) {
        Object data = post(url, resBody, reqHeaderMap);
        httpServletResponse.setContentType(contentTypeEnum.getContentType());
        try {
            write(httpServletResponse, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 通用方法
     * @param url
     * @param params
     * @param reqHeaderMap
     * @param responseType
     * @param <T>
     * @return
     */
    private static <T> T getUtil(String url, Map<String, Object> params, HashMap<String, String> reqHeaderMap, Class<T> responseType) {
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

        ResponseEntity<T> res = restTemplate.exchange(
                url + sj,
                HttpMethod.GET,
                requestEntity,
                responseType
        );
        return res.getBody();
    }

    /**
     * 通用方法
     *
     * @param url
     * @param resBody
     * @param reqHeaderMap
     * @param responseType
     * @param <T>
     * @return
     */
    private static <T> T postUtil(String url, Map<String, Object> resBody, HashMap<String, String> reqHeaderMap, Class<T> responseType) {
        //请求头
        HttpHeaders headers = new HttpHeaders();
        if (reqHeaderMap != null) {
            reqHeaderMap.forEach(headers::set);
        }
        //请求体
        String requestBody = JSON.toJSONString(resBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<T> res = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        return res.getBody();
    }


    /**
     * 写入文件
     *
     * @param httpServletResponse 响应
     * @param data                数据
     */
    private static void write(HttpServletResponse httpServletResponse, Object data) throws IOException {
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.println(data);
            writer.flush();
        } catch (IOException e) {
            writeWrong(httpServletResponse, httpServletResponse.getWriter(), e);
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 写入报错
     *
     * @param httpServletResponse 响应
     * @param writer              写入
     * @param e                   错误
     */
    private static void writeWrong(HttpServletResponse httpServletResponse, PrintWriter writer, Exception e) {
        if (writer == null) {
            return;
        }
        writer.println("出错" + e.getMessage());
        httpServletResponse.setContentType(ContentTypeEnum.TEXT_PLAIN.getContentType());
    }


}
