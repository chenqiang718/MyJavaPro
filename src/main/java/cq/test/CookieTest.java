package cq.test;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CookieTest {
    public static void main(String[] args) {
        WebDriver driver=null;
        try {
            System.setProperty("webdriver.firefox.bin", "D://Mozilla Firefox/Mozilla Firefox/firefox.exe");
            System.setProperty("webdriver.gecko.driver", "D://Mozilla Firefox/Mozilla Firefox/geckodriver.exe");
            System.out.println("start");
            driver = new FirefoxDriver();
            driver.get("https://www.ele.me/place/wtm7zemsxy65?latitude=30.209089&longitude=120.220291");
            driver.manage().deleteAllCookies();
            String cookie="ubt_ssid=366iv1hfjaxug2379ngk9dwx596t0o69_2018-06-19; _utrace=c5c1eb48995d560e9bed3b6ce6e3674f_2018-06-19; eleme__ele_me=1d6930211621cd116f3a5bb554ba38f3%3Aa8a813f16172e25d3001b0f1da7adcf423f28e96; perf_ssid=xn5q6u4uq1ai0ihg63on5iiz12uywywm_2018-06-28; track_id=1529397859|dedb8fac9b7aa9076a3bcd96adeda3cbe738b5c0627aee6e44|750ad091af4c7eef1c222e9610b3844d; USERID=12118872; SID=40QxfKBKi9kRlMlL3zkVFxYPxQplqqFwvYUw";
            addCookie(driver, cookie);
            Thread.sleep(3000);// 沉睡3秒
            String value=driver.manage().getCookieNamed("ubt_ssid").getValue();
            System.out.println(value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void addCookie(WebDriver driver, Map<String, String> args) {
        Set<String> keys = args.keySet();
        for(String key : keys){
            driver.manage().addCookie(new Cookie(key, args.get(key)));
        }
    }

    public static void addCookie(WebDriver driver,String argsStr) {
        Map<String, String> args=getmap(argsStr);
        Set<String> keys = args.keySet();
        for(String key : keys){
            driver.manage().addCookie(new Cookie(key, args.get(key)));
        }
    }

    private static Map<String,String> getmap(String infomation){
        Map<String,String> map=new HashMap<>();
        String[] items=infomation.split(";");
        for (String item:items){
            String name=item.substring(item.indexOf("="));
            String value=item.substring(item.indexOf("=")+1,item.length() );
            map.put(name, value);
        }
        return map;
    }
}
