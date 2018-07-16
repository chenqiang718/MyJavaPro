package cq.crawl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import cq.common.ExcelUtils;
import cq.common.ReadExcel;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class eleme_place {
    private static Logger logger = Logger.getLogger(eleme_place.class);
    static String ip="122.114.31.177";
    static int port=808;

    public static void main(String[] args) throws IOException, InterruptedException {
        ReadExcel readExcel = new ReadExcel();
        File file = new File("D:/滨江小区数据.xlsx");
        String tempUrl = "https://www.ele.me/restapi/v2/pois?extras%5B%5D=count&geohash=wtmknpnr9yy3&keyword=${kw}&limit=20&type=nearby";
        List<Map<String, String>> list = readExcel.getExcelInfo(file);
        JSONArray head = new JSONArray();
        JSONArray body = new JSONArray();
        /*        *//*JVM设置代理*//*
        System.getProperties().setProperty("http.proxyHost", ip);
        System.getProperties().setProperty("http.proxyPort", "80");*/
        for (Map<String, String> map : list) {
            String kw = map.get("keyWord");
            String url = tempUrl.replace("${kw}", kw);
            HttpGet http = new HttpGet(url);
            http.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

            CloseableHttpResponse response = null;
            try {
                response = HttpClients.createDefault().execute(http);
//                response = getHttpClient().execute(http);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                String data = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(data);
                JSONArray jsonArray = JSONArray.parseArray(data);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (object.getString("city").indexOf("滨江区") > 0) {
                        String name = object.getString("name");
                        String placeId = object.getString("geohash");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("keyWord", kw);
                        jsonObject.put("place", name);
                        jsonObject.put("placeId", placeId);
                        body.add(jsonObject);
                        logger.info("kw:" + kw + " place:" + name + " placeId" + placeId + "\n");
                    }
                }
                Thread.sleep(2000);
            } else {
                logger.info("当前状态为:{};\t内容为:{}" + status + EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        }
        head.add("关键字");
        head.add("地区");
        head.add("地区ID");
        HSSFWorkbook hwb = ExcelUtils.expExcel("滨江地区地点数据",head, body);
        ExcelUtils.outFile(hwb, "D:/滨江小区饿了么placeId.xlsx");
    }
    public static CloseableHttpClient getHttpClient(){
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
