package cq.crawl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cq.common.ExcelUtils;
import cq.common.ReadExcel;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Doumi {
    private static Logger logger = Logger.getLogger(Doumi.class);
    static String ip="122.114.31.177";
    static int port=808;
    static String tableName="斗米兼职网数据";
    static String fileloaction="D:/斗米兼职网数据.xlsx";

    public static void main(String[] args) {
        String[] arr={"content","worktime","worktype","workplace","peopleNumber","salary"};
        String tempUrl = "http://www.doumi.com/hz/";
        CloseableHttpResponse response = null;
        try {

            for (int page = 0; page < 11; page++) {
                String url = tempUrl;
                if (page > 0) {
                    url = tempUrl + "o" + page + "/";
                }
                HttpGet http = new HttpGet(url);
                http.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

                response = HttpClients.createDefault().execute(http);
//                response = getHttpClient().execute(http);
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    String data = EntityUtils.toString(response.getEntity(), "utf-8");
                    Document document = Jsoup.parse(data);
                    Elements items = document.select(".jzList-con .jzList-item");
                    JSONArray head = new JSONArray();
                    for (int i = 0; i < arr.length; i++) {
                        head.add(arr[i]);
                    }
                    JSONArray body = new JSONArray();
                    for (Element item : items) {
                        String content = item.select("a:eq(0)").text();
                        String worktime = item.select("ul li:eq(0)").text();
                        String worktype = item.select("ul li:eq(1)").text();
                        String workplace = item.select("ul li:eq(2)").text();
                        String peopleNumber = item.select("ul li:eq(3)").text();
                        String salary = item.select(".jzList-salary").text();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("content", content);
                        jsonObject.put("worktime", worktime);
                        jsonObject.put("worktype", worktype);
                        jsonObject.put("workplace", workplace);
                        jsonObject.put("peopleNumber", peopleNumber);
                        jsonObject.put("salary", salary);
                        body.add(jsonObject);
                        System.out.println(content);
                    }
                    HSSFWorkbook hwb=null;
                    if(page>0){
                        hwb=ExcelUtils.expExcel(new File(fileloaction), head, body);
                    } else{
                        hwb= ExcelUtils.expExcel(tableName + page, head, body);
                    }
                    ExcelUtils.outFile(hwb, fileloaction);
                } else {
                    logger.info("当前网址："+url+" 异常，状态为："+status);
//                    logger.info("当前状态为:{};\t内容为:{}" + status + EntityUtils.toString(response.getEntity(), "utf-8"));
                }
                Thread.sleep(5000);
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
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
