package cq.cq.test;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chenqiang
 * @Date: 2019/1/31 16:34
 * @Version 1.0
 */
public class PostTest {
    public static void main(String[] args) {
        PostTest postTest=new PostTest();
        String url="https://10.2.1.59:8082/getWord";
        Map<String,String> map=new HashMap<>();
        map.put("referer", "https://search.jd.com/Search");
        String value="";
        try {
            value=postTest.readHtml(url, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(value);
    }

    public String readHtml(String url, Map<String,String> map) throws IOException, InterruptedException {
        String str="";
        CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建httpClient实例
        HttpPost httpPost = new HttpPost(url); // 创建httpget实例
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        map.entrySet().forEach((entry)->{
            httpPost.setHeader(entry.getKey(),entry.getValue());
        });
        String postParam = "test";//请求的参数内容
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("data", postParam));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost); // 执行http get请求
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity(); // 获取返回实体
            str=EntityUtils.toString(entity, "utf-8");
            response.close(); // response关闭
            httpClient.close(); // httpClient关闭
            return str;
        }
        return "";
    }
}
