package com.cycas.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cycas.util.dto.HttpConfigDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static HttpClientUtils instance = null;

    private HttpClientUtils() {}

    public static HttpClientUtils getInstance() {

        if (instance == null) {
            instance = new HttpClientUtils();
        }
        return instance;
    }

    private static String sendHttpPost(String httpUrl, String jsonData, Map<String, String> headerMap, String encoding) {

        HttpPost httpPost = new HttpPost(httpUrl);
        // 填充请求头
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        httpPost.setHeader("Content-type", "application/json");
        httpPost.addHeader("Accept-Charset", "UTF-8");
        StringEntity stringEntity = new StringEntity(jsonData, encoding);
        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding(encoding);
        httpPost.setEntity(stringEntity);
        return sendHttpPost(httpPost, encoding);
    }


    private static String sendHttpPost(HttpPost httpPost, String encoding) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = "";
        try {
            httpClient = HttpClientBuilder.create().build();
//            httpPost.setConfig(requestConfig);
            logger.info("HttpClient Final SendHttpPost {}", httpPost);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, encoding);
            }
            logger.info("HttpClient sendHttpPost Response {}", response);
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            logger.error("执行post请求失败了！", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("关闭资源失败了！", e);
            }
        }
        return responseContent;
    }

    public static String getAccessTokenFromBangBang() {

        // 获取accessToken参数
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("appKey", "b0f46f598f6c936d");
        dataMap.put("appSecret", "cee435f21198dc608510df2e61ee438a");
        String url = "http://devp.bangbangyouxin.cn:11117/openservice/partner/access_token.do";
        String responseJson = sendHttpPost(url, JSONObject.toJSONString(dataMap), new HashMap<>(), "UTF-8");
        logger.info(responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        return jsonObject.getString("accessToken");
    }

    public static void buildSign() {

        // 请求参数
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("appKey", "b0f46f598f6c936d");
        dataMap.put("orderNo", "80122128082801122246");
        dataMap.put("notificationType", "INSTALLMENT_ORDER_CANCELLATION");
        dataMap.put("oredCancellationTime", "2021-01-14 16:51:11");
        // 生成签名
        String sign = FddEncryptTool.buildSignBySHA256(dataMap, "cee435f21198dc608510df2e61ee438a");
        logger.info("-----sign------:{}", sign);
    }

    public static void getInstallmentFromBangBang() {

        // 请求参数
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("appKey", "b0f46f598f6c936d");
        dataMap.put("orderNo", "80122128082801122246");
        dataMap.put("mobile", "18045622285");
        // 生成签名
        String sign = FddEncryptTool.buildSignBySHA256(dataMap, "cee435f21198dc608510df2e61ee438a");
        logger.info("sign:{}", sign);
        dataMap.put("sign", sign);
        // 获取accessToken
        String accessToken = getAccessTokenFromBangBang();
        logger.info("accessToken:{}", accessToken);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("AccessToken", accessToken);
        dataMap.put("accessToken", accessToken);
        String url = "http://devp.bangbangyouxin.cn:11117/openservice/partner/installment/order/info.do";
        String responseJson = sendHttpPost(url, JSONObject.toJSONString(dataMap), headerMap, "UTF-8");
        logger.info("--------------responseJson---------------:{}", responseJson);
    }

    private static void pay(){

        List<Map> list = new ArrayList<>();
        TreeMap<String,String> maps1 = new TreeMap<>();
        maps1.put("termNo","1");
        maps1.put("termDeadline","2021-02-13");
        maps1.put("termAmount","2000.00");
        maps1.put("paymentAmount","2000.00");
        maps1.put("paymentStatus","1");
        maps1.put("paymentTime","2021-01-14 19:32:22");
        list.add(maps1);
        TreeMap<String,String> maps2 = new TreeMap<>();
        maps2.put("termNo","2");
        maps2.put("termDeadline","2021-03-13");
        maps2.put("termAmount","2000.00");
        maps2.put("paymentAmount","2000.00");
        maps2.put("paymentStatus","1");
        maps2.put("paymentTime","2021-01-14 19:32:22");
        list.add(maps2);
        TreeMap<String,String> maps3 = new TreeMap<>();
        maps3.put("termNo","3");
        maps3.put("termDeadline","2021-04-13");
        maps3.put("termAmount","2000.00");
        maps3.put("paymentAmount","2000.00");
        maps3.put("paymentStatus","1");
        maps3.put("paymentTime","2021-01-14 19:32:22");
        list.add(maps3);

        TreeMap<String, String> dataMap = new TreeMap<>();
        dataMap.put("appKey","b0f46f598f6c936d");
        dataMap.put("orderNo","80122128082801122246");
        dataMap.put("notificationType","INSTALLMENT_ORDER_PAYMENT");
        dataMap.put("installmentList", JSONObject.toJSONString(list));

        // 生成签名
        String sign = FddEncryptTool.buildSignBySHA256(dataMap, "cee435f21198dc608510df2e61ee438a");
        dataMap.put("sign", sign);
        // 获取accessToken
        String accessToken = getAccessTokenFromBangBang();
        logger.info("accessToken:{}", accessToken);
        dataMap.put("accessToken", accessToken);
        String url = "http://devmcapi.bjmantis.net/hc-api/bangCallback/installmentPaymentNotice";
        String responseJson = sendHttpPost(url, JSONObject.toJSONString(dataMap), new HashMap<>(), "UTF-8");
        logger.info("--------------responseJson---------------:{}", responseJson);
    }

    public static void main(String[] args) {

    }


}
