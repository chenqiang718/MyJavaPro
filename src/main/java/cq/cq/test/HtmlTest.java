package cq.cq.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HtmlTest {

    public String readHtml(String url) throws IOException, InterruptedException {
        String str="";
        CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建httpClient实例
        HttpGet httpGet = new HttpGet(url); // 创建httpget实例
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
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

    public List<JSONObject> dealResponse(String response){
        List<JSONObject> jsonObjectList=new ArrayList<>();
        Document document=Jsoup.parse(response);
        Elements trs=document.select("#ip_list tr");
        JSONObject proxy=null;
        for(int i=1;i<trs.size();i++){
            String country=trs.get(i).select("td:eq(0) img").attr("alt");
            String ip=trs.get(i).select("td:eq(1)").text();
            String port=trs.get(i).select("td:eq(2)").text();
            String area=trs.get(i).select("td:eq(3)").text();
            String niming=trs.get(i).select("td:eq(4)").text();
            String type=trs.get(i).select("td:eq(5)").text();
            String speed=trs.get(i).select("td:eq(6) .bar").attr("title");
            String linkTime=trs.get(i).select("td:eq(7) .bar").attr("title");
            String liveTime=trs.get(i).select("td:eq(8)").text();
            String valiTime=trs.get(i).select("td:eq(9)").text();
            proxy=new JSONObject();
            proxy.put("country",country);
            proxy.put("ip",ip);
            proxy.put("port",port);
            proxy.put("area",area);
            proxy.put("niming",niming);
            proxy.put("type",type);
            proxy.put("speed",speed);
            proxy.put("linkTime",linkTime);
            proxy.put("liveTime",liveTime);
            proxy.put("valiTime",valiTime);
            jsonObjectList.add(proxy);
            proxy=null;
        }
        return jsonObjectList;
    }

    public static void main(String[] args) {
        HtmlTest htmlTest=new HtmlTest();
        try {
            String response=htmlTest.readHtml("http://www.xicidaili.com/nn");
            List<JSONObject> jsonObjectList=htmlTest.dealResponse(response);
            System.out.println("-----------------------------");
            for(JSONObject jsonObject:jsonObjectList){
                StringBuffer sb=new StringBuffer("{"+"\n");
                Set<Map.Entry<String, Object>> entrySet= jsonObject.entrySet();
                for (Map.Entry<String,Object> map:entrySet){
                    sb.append(map.getKey()+":"+map.getValue()+"\n");
                }
                sb.append("}");
                System.out.println(sb.toString());
            }
//            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
