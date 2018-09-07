package cq.browser;

import cq.common.ScriptIn;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.Map;

public class Weibo {
    private static Logger logger=Logger.getLogger(Weibo.class);
    static String url="https://weibo.com/1769962140/GtGng7SXz?from=page_1006061769962140_profile&wvr=6&mod=weibotime&type=comment";
    public static void main(String[] args) {
        WebDriver driver=null;
        try {
            System.setProperty("webdriver.firefox.bin", "D://Mozilla Firefox/Mozilla Firefox/firefox.exe");
            System.setProperty("webdriver.gecko.driver", "D://Mozilla Firefox/Mozilla Firefox/geckodriver.exe");
            logger.info("start");
            String scrollToBottom="var h = $(document).height() - $(window).height();\n" +
                    "    $(document).scrollTop(h);";
            String getData="function getConnection(s) {\n" +
                    "    var content = s.substring(s.indexOf(\"：\") + 1);\n" +
                    "    return content;\n" +
                    "}\n" +
                    "var data = [];\n" +
                    "    $('.list_li.S_line1.clearfix').each(function() {\n" +
                    "        data.push({\n" +
                    "            rateId: $(this).attr('comment_id'),\n" +
                    "            user: $('.WB_text:eq(0) a:eq(0)', this).text(),\n" +
                    "            userId: $('.WB_text:eq(0) a:eq(0)', this).attr('usercard').replace('id=', ''),\n" +
                    "            pubTime: $('.WB_from.S_txt2:eq(0)', this).text(),\n" +
                    "            content: getConnection($('.WB_text:eq(0)', this).text())\n" +
                    "        });\n" +
                    "    });\n" +
                    "    return data;";
            driver = new FirefoxDriver();
            driver.get(url);
            Thread.sleep(3000);// 沉睡3秒
            driver.get(url);
            Thread.sleep(3000);// 沉睡3秒
            ScriptIn.runJs(driver, scrollToBottom);
            Thread.sleep(3000);// 沉睡3秒
            ScriptIn.runJs(driver, scrollToBottom);
            Thread.sleep(3000);// 沉睡3秒
            ScriptIn.runJs(driver, scrollToBottom);
            int i=0;
//            Map<String,Object> datas=null;
            List<Map<String,Object>> datas=null;
            while(true){
                List<WebElement> more_txt=((FirefoxDriver) driver).findElementsByClassName("more_txt");
                i++;
                if(more_txt.size()>0){
                    ScriptIn.runJs(driver, "$('.more_txt').click()");
                    Thread.sleep(2000);
                    System.out.println("次数:"+i);
                    continue;
                }
                datas= (List<Map<String, Object>>) ScriptIn.runJs(driver, getData);
                break;
            }
            if(datas==null || datas.size()<1){
                System.out.println("数据为空");
            }else {
                for(Map.Entry<String,Object> data:datas.get(0).entrySet()){
                    System.out.println(data.getKey()+":"+data.getValue());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(driver!=null){
                driver.close();
                driver.quit();
            }

        }
    }
}
