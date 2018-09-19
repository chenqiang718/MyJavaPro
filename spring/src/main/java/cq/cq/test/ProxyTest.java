package cq.cq.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ProxyTest {
    private String ip;
    private int port;

    public ProxyTest(){}

    public ProxyTest(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static boolean testProxy(String ip,int port) {
        HttpHost proxy = new HttpHost(ip, port);
//        CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建httpClient实例
        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).setConnectionRequestTimeout(3000).setSocketTimeout(3000).build();
        CloseableHttpClient httpClient=HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet("https://www.baidu.com/"); // 创建httpget实例
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(httpGet); // 执行http get请求
            //HttpEntity entity = response.getEntity(); // 获取返回实体
            //System.out.println("网页内容：" + EntityUtils.toString(entity, "utf-8")); // 获取网页内容
        }
        catch (Exception err){
            return false;
        }finally {
            if(response!=null) {
                try {
                    response.close(); // response关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpClient!=null){
                try {
                    httpClient.close(); // httpClient关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(response.getStatusLine().getStatusCode()>=200 && response.getStatusLine().getStatusCode()<300){
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建httpClient实例
        HttpGet httpGet = new HttpGet("https://www.baidu.com/"); // 创建httpget实例
        HttpHost proxy = new HttpHost("111.194.12.152", 8118);
        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        CloseableHttpResponse response = httpClient.execute(httpGet); // 执行http get请求
        HttpEntity entity = response.getEntity(); // 获取返回实体
        System.out.println("网页内容：" + EntityUtils.toString(entity, "utf-8")); // 获取网页内容
        response.close(); // response关闭
        httpClient.close(); // httpClient关闭
    }
}
