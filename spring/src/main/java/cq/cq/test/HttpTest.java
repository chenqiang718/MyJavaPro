package cq.cq.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenqiang
 * @Date: 2019/1/24 14:47
 * @Version 1.0
 */
public class HttpTest {
    public static void main(String[] args) {
        HttpTest httpTest=new HttpTest();
        String url="https://search.jd.com/s_new.php?keyword=三草两木Saselomo旗舰店&enc=utf-8&page=1&s=10000&scrolling=y";
        Map<String,String> map=new HashMap<>();
        map.put("referer", "https://search.jd.com/Search");
        String value="";
        try {
            value=httpTest.readHtml(url, map);
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
        HttpGet httpGet = new HttpGet(url); // 创建httpget实例
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        map.entrySet().forEach((entry)->{
            httpGet.setHeader(entry.getKey(),entry.getValue());
        });
        CloseableHttpResponse response = httpClient.execute(httpGet); // 执行http get请求
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
