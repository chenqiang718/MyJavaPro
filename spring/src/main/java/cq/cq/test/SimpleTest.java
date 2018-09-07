package cq.cq.test;

import cq.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈强
 * @Date: 2018/8/29 17:00
 * @Version 1.0
 */
public class SimpleTest {
    public static void main(String[] args) {
        Map<String,List<String>> map=new HashMap<>();
        List<String> stringList=new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        map.put("ceshi",stringList );

        for(Map.Entry<String,List<String>> entry:map.entrySet()){
            for(String str:entry.getValue()){
                System.out.println(str);
            }
        }
    }
}
