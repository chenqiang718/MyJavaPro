package cq.cq.test;

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
            //driver.get("https://www.ele.me/place/wtm7zemsxy65?latitude=30.209089&longitude=120.220291");
            driver.get("https://account.aliyun.com/login/login.htm?oauth_callback=https%3A%2F%2Fswas.console.aliyun.com%2F%3Fspm%3D5176.6660585.774526198.1.c8446bf8ZUt9ZH#/server/8d2f4bb20d134416b0e9f65006b9c13c/cn-shenzhen/security/firewall");
            driver.manage().deleteAllCookies();
            String cookie="cna=Pv9REzdGuzQCAXPsIAJsk1VS; cnz=jpIrFJr0HDECAQIg7HOpQJ7J; CLOSE_HELP_GUIDE=true; ONE_NEW_JSESSIONID=516668D1-H0MYXTRWU60MG2KQVZSZ1-VRFSFHMJ-D8C1; ping_test=true; t=21fcab6ca34f46f8c8680960591cf805; _tb_token_=fe8a33313757f; cookie2=1a9e834c35633a1ae0c88ef06a8c4be0; _hvn_login=6; csg=b9beefaf; login_aliyunid=\"135920****@qq.com\"; login_aliyunid_ticket=8s*MT5tJl3_1$$w3TLxiBYF_B2JkH0I7uml4ElloQhLZSMeYKZsxc*qsOf_ENpoU_BOTwChTBoNM1ZJeedfK9zxYnbN5hossqIZCr6t7SGxRigm2Cb4fGaCdBZWIzmgdHq6sXXZQg4KFWufyvpeV*0mC0; login_aliyunid_csrf=_csrf_tk_1216537862792086; login_aliyunid_suid=\"XLTrmTLnuAyO3aKCNAVSvJHeBQTI4AN2sbpyc6ppgCgFozt6hxE=\"; login_aliyunid_pk=1684521628154430; login_aliyunid_pks=; login_aliyunid_token=\"t+dRwnT5/TbhUC0i0mP2un+7BRULA/wQlLY1EIspjRY=\"; hssid=19TharLA34Fo1bDPHCMjzIg1; hsite=6; aliyun_country=CN; aliyun_site=CN; aliyun_lang=zh; _one_new_session0=omuVzs9fdEzCCDs052SstffZCTd8VKQopqDwvV5MtcSdVVi9GT8fKbZdhZAor7f%2BacfWpv6yFtZm9vYAQY8R5bORQ13FYIfSX7mK9lFQcADMUIGq6s8nQh3OYY3E4fpshMutVh9tKv0N7adqQ%2FkcelNf8y3wFoY90%2Fo0%2BSuZ1uR8fPoQPDm%2BHVWm43iR6G02bj%2Btb8dmSic2dwTP1lNN2MLfvU8xEgUL0Xfhe6uR6hV2Ei%2B%2FauunqgrtFdGw28w8sY9gkQpfUD8pD76gf8dA90MbrGSsNA4N2j1EfxFFQgDXtbYbuKexkB66rmEoapjYcJlmkgqcRY3%2ByBUGabG6JA%3D%3D; _uab_collina=153786279345431465295438; consoleNavVersion=1.4.116; _HOME_JSESSIONID=02666H71-NYIY12SPU7ZULE8YM2WY1-9DUSFHMJ-B6Q7; _home_session0=WoSyfIOIszdSbR5eNII89YTXjCexjQ62HvlvRImZiiie1ycjRwdDqxrHPjpZ4iDToyyQLLDO6IRY7CBqb5WDTRJSzSpL2c8dqjXcNQjd93jCDg%2BUC0VO5Q2XPZdSGl4EXzOYL4P6Y0xyDRiF50781279gxyABx4lR9h8PzEHzo8LtAg82oc%2B4r5RNV%2BhnlMcpsBQYhaY5hVz%2BPvvKeAkNIWWkjnKXJtrv6Fz1DoL9cIr8%2F78yxFGKykN7elS5DnNjOGRt9JNzdDuR7Iic%2Fw3L%2BJwJjBJyd2I%2Fdm2KhLl3vCPlSP3NG77LZ%2BoIZTXMBqjyHGQ2Re5OW4BqbUUiSardg%3D%3D; _umdata=85957DF9A4B3B3E8C53ECC9C2E078E0ED613A3C6B1C11035B4CE6BA68CB1FE85F7B64CBCE8277637CD43AD3E795C914CD58E265A697102EFE2E2906CC29206B1; consoleRecentVisit=swas; isg=BLq62XlatZwM1Dl9VamEl3U7C-DWay_z_uzCscSzaM0Yt1rxrf8tVLjFAwPOJ7bd";
            addCookie(driver, cookie);
            //String value0=driver.manage().getCookieNamed("ubt_ssid").getValue();
            //System.out.println("0:"+value0);
            //driver.navigate().refresh();
            Thread.sleep(3000);// 沉睡3秒
            //String value1=driver.manage().getCookieNamed("ubt_ssid").getValue();
            //System.out.println("1:"+value1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(driver!=null) driver.quit();
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
            String name=item.substring(0,item.indexOf("="));
            String value=item.substring(item.indexOf("=")+1,item.length() );
            map.put(name, value);
        }
        return map;
    }
}
