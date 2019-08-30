package cq.utils;

import cq.common.CommonError;
import cq.common.FateException;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author: Chen Qiang
 * @Date: 2019-08-30 13:58
 * @description
 */
public class HttpUtil {

    public static String readHtml(HttpUriRequest request) throws Exception{
        String data="";

        CloseableHttpResponse response = null;
        try {
            response = HttpClients.createDefault().execute(request);
        } catch (IOException e) {
            throw e;
        }
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            data = EntityUtils.toString(response.getEntity(), "utf-8");
        } else {
            throw new FateException(CommonError.WEB_STATUS_ERROR);
        }
        return data;
    }

    public static String readHtmlWithProxy(HttpUriRequest request,String ip,int port) throws Exception{
        String data="";

        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(ip,port).execute(request);
        } catch (IOException e) {
            throw e;
        }
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            data = EntityUtils.toString(response.getEntity(), "utf-8");
        } else {
            throw new FateException(CommonError.WEB_STATUS_ERROR);
        }
        return data;
    }

    public static CloseableHttpClient getHttpClient(String ip,int port) {
        //设置代理IP、端口、协议（请分别替换）
        HttpHost proxy2 = new HttpHost(ip, port);

        //把代理设置到请求配置
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setProxy(proxy2)
                .build();

        //实例化CloseableHttpClient对象CloseableHttpResponse
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        return httpclient;
    }
}
