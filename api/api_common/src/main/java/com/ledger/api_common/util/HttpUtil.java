package com.ledger.api_common.util;

import cn.hutool.core.util.StrUtil;
import com.ledger.api_common.Exception.LedgerException;
import com.ledger.api_common.annotation.ledgerApi;
import com.ledger.api_common.enums.Method;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.ledger.api_common.enums.ContentTypeEnum;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class HttpUtil {
    private static final RestTemplate restTemplate;
    private static final RestTemplate restTemplate0;

    static {
        try {
            //不伪造证书
            restTemplate = new RestTemplate(generateHttpRequestFactory());
            //伪造证书
            restTemplate0 = new RestTemplate();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }

    public static HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        return factory;
    }


    /**
     * get请求(通用)
     *
     * @param url          地址
     * @param params       参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static String get(String url, Map<String, Object> params, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        return getUtil(url, params, reqHeaderMap, String.class, isNeedCertificate);
    }

    /**
     * post请求(通用)
     *
     * @param url          地址
     * @param resBody      参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static String post(String url, Map<String, Object> resBody, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        return postUtil(url, resBody, reqHeaderMap, String.class, isNeedCertificate);
    }

    /**
     * get请求(字节数组)
     *
     * @param url          地址
     * @param params       参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static byte[] getByteArr(String url, Map<String, Object> params, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        return getUtil(url, params, reqHeaderMap, byte[].class, isNeedCertificate);
    }

    /**
     * post请求(字节数组)
     *
     * @param url          地址
     * @param resBody      参数
     * @param reqHeaderMap 请求头
     * @return 请求示例
     */
    public static byte[] postByteArr(String url, Map<String, Object> resBody, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        return postUtil(url, resBody, reqHeaderMap, byte[].class, isNeedCertificate);
    }

    /**
     * get将文件转换为json字符串
     *
     * @param url          url
     * @param params       路径参数
     * @param reqHeaderMap 请求头
     * @return 返回json
     */
    public static JSON getJson(String url, Map<String, Object> params, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        Object data = get(url, params, reqHeaderMap, isNeedCertificate);
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
    public static JSON postJson(String url, Map<String, Object> resBody, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        Object data = post(url, resBody, reqHeaderMap, isNeedCertificate);
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
                                  ContentTypeEnum contentTypeEnum,
                                  boolean isNeedCertificate) {
        Object data = get(url, params, reqHeaderMap, isNeedCertificate);
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
                                   ContentTypeEnum contentTypeEnum,
                                   boolean isNeedCertificate) {
        Object data = post(url, resBody, reqHeaderMap, isNeedCertificate);
        httpServletResponse.setContentType(contentTypeEnum.getContentType());
        try {
            write(httpServletResponse, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> JSON getLedgerApiJson(T T, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        return JSON.parseObject(getLedgerApiData(T, reqHeaderMap, String.class, isNeedCertificate), JSON.class);
    }
    public static <T> byte[] getLedgerApiByteArr(T T, HashMap<String, String> reqHeaderMap, boolean isNeedCertificate) {
        return getLedgerApiData(T, reqHeaderMap, byte[].class, isNeedCertificate);
    }


    public static <T,K> K getLedgerApiData(T T, HashMap<String, String> reqHeaderMap,Class<K> K, boolean isNeedCertificate) {
        Class<?> clazz = T.getClass();
        boolean hasAnnotation = clazz.isAnnotationPresent(ledgerApi.class);
        if (hasAnnotation) {
            ledgerApi annotation = clazz.getAnnotation(ledgerApi.class);
            String url = annotation.url();
            Method method = annotation.method();
            String AccessKey = annotation.AccessKey();
            String SecretKey = annotation.SecretKey();
            if (StrUtil.isBlank(AccessKey) || StrUtil.isBlank(SecretKey)) {
                throw new LedgerException("请提供相应的AccessKey和SecretKey");
            }
            Field[] declaredFields = clazz.getDeclaredFields();
            HashMap<String, Object> params = new HashMap<>();
            if (reqHeaderMap == null) {
                reqHeaderMap = new HashMap<>();
            }
            reqHeaderMap.put("AccessKey", AccessKey);
            reqHeaderMap.put("SecretKey", SecretKey);
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try {
                    Object o = field.get(T);
                    if (o instanceof String || o instanceof Integer || o instanceof Long || o instanceof Boolean || o instanceof Float || o instanceof Double || o==null) {
                        params.put(field.getName(), o);
                    } else {
                        throw new LedgerException("不合法的参数类型");
                    }
                } catch (IllegalAccessException e) {
                    throw new LedgerException("获取注解失败,请检查是否正确注解了@ledgerApi");
                }
            }
            if (method.equals(Method.GET)) {
                return getUtil(url, params, reqHeaderMap, K, isNeedCertificate);
            } else if (method.equals(Method.POST)) {
                return postUtil(url, params, reqHeaderMap, K, isNeedCertificate);
            }
        } else {
            throw new LedgerException("请将注解@ledgerApi加上去,并提供相应的url");
        }
        return null;
    }






    /**
     * 通用方法
     *
     * @param url
     * @param params
     * @param reqHeaderMap
     * @param responseType
     * @param <T>
     * @return
     */
    private static <T> T getUtil(String url,
                                 Map<String, Object> params,
                                 HashMap<String, String> reqHeaderMap,
                                 Class<T> responseType,
                                 boolean isNeedCertificate) {
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
        ResponseEntity<T> res;
        if (isNeedCertificate) {
            res = restTemplate.exchange(
                    url + sj,
                    HttpMethod.GET,
                    requestEntity,
                    responseType
            );
        } else {
            res = restTemplate0.exchange(
                    url + sj,
                    HttpMethod.GET,
                    requestEntity,
                    responseType
            );
        }
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
    private static <T> T postUtil(String url,
                                  Map<String, Object> resBody,
                                  HashMap<String, String> reqHeaderMap,
                                  Class<T> responseType,
                                  boolean isNeedCertificate) {
        //请求头
        HttpHeaders headers = new HttpHeaders();
        if (reqHeaderMap != null) {
            reqHeaderMap.forEach(headers::set);
        }
        //请求体
        String requestBody = JSON.toJSONString(resBody);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> res;
        if (isNeedCertificate) {
            res = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    responseType
            );
        } else {
            res = restTemplate0.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    responseType
            );
        }

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
