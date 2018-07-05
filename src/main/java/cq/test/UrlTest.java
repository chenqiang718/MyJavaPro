package cq.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlTest {
    public static void main(String[] args) {
        String url="http://waimai.meituan.com/geo/geohash?lat=30.21584&lng=120.226935&addr=%25E6%25B5%25B7%25E5%25BA%25B7%25E5%25A8%2581%25E8%25A7%2586%25E6%2595%25B0%25E5%25AD%2597%25E6%258A%2580%25E6%259C%25AF%25E8%2582%25A1%25E4%25BB%25BD%25E6%259C%2589%25E9%2599%2590%25E5%2585%25AC%25E5%258F%25B8&from=m";
        try {
            String dUrl=URLDecoder.decode(url, "utf-8");
            System.out.println(dUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
