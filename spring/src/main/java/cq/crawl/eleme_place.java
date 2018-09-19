package cq.crawl;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

public class eleme_place {
    private static Logger logger = Logger.getLogger(eleme_place.class);
    static String ip = "111.194.12.152";
    static int port = 8118;

    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://h5.ele.me/restapi/shopping/v1/restaurants/E5502619504987666116/business/qualification?latitude=30.209089&longitude=120.220291&terminal=h5";

//JVM设置代理

        HttpGet http = new HttpGet(url);
        http.setHeader("Cookie", "ubt_ssid=366iv1hfjaxug2379ngk9dwx596t0o69_2018-06-19; _utrace=c5c1eb48995d560e9bed3b6ce6e3674f_2018-06-19; perf_ssid=rwlzfnu5han3a3o4nagh8hfd2lrlfggz_2018-06-19; perf_ssid=xn5q6u4uq1ai0ihg63on5iiz12uywywm_2018-06-28; track_id=1529397859|dedb8fac9b7aa9076a3bcd96adeda3cbe738b5c0627aee6e44|750ad091af4c7eef1c222e9610b3844d; USERID=12118872; SID=sgQa5ViXfIp1VPTPzwtjIETkN3DVPVTghLvA");
        http.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

        CloseableHttpResponse response = null;
        try {
//            response = HttpClients.createDefault().execute(http);
                response = getHttpClient().execute(http);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            String data = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(data);
            JSONArray jsonArray = JSONArray.parseArray(data);
            Thread.sleep(2000);
        } else {
            logger.info("当前状态为:{};\t内容为:{}" + status + EntityUtils.toString(response.getEntity(), "utf-8"));
        }
    }

    public static CloseableHttpClient getHttpClient() {
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
