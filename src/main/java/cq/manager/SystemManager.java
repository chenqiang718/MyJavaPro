package cq.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cq.Model.Seed;
import cq.Service.SeedService;
import cq.common.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class SystemManager {
    @Autowired
    private SeedService seedService;
    public void init(){
//        runinsert();
        System.out.println("你好");
        runTest2();
    }

    private void runinsert(){
        String file="D:/2018/互金url.xlsx";
        List<JSONObject> jsonObjectList=null;
        try {
            jsonObjectList=seedService.readExcelFile(new File(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(jsonObjectList!=null){
            for(JSONObject jsonObject:jsonObjectList){
                String company=jsonObject.getString("1");
                String url=jsonObject.getString("2");
                List<Seed> seedList=seedService.selectSeed(url);
                if(seedList.size()>0){
                    Seed seed=seedList.get(0);
                    String oldcontent=seed.getTask_content();
                    if(seedService.selectSeed("tag").size()<1){
                        seed.setTask_content((oldcontent.substring(0, oldcontent.length()-1)+",\"tag\":\"金融\"}").replace("\"","\\\"" ));
                        seedService.updateSeed(seed);
//                        System.out.println(seed.getTask_content());
                    }
                }else if(seedList.size()==0){
                    String content=("{'name':'"+company+"','demain':'"+url+"','tag':'金融'}");
                    Seed seed=new Seed();
                    seed.setMedia_cd("gs_company");
                    seed.setTask_type("link");
                    seed.setTask_content(content);
                    seed.setInterval(40320);
                    seed.setRemark(company);
                    seed.setItems(49);
                    seed.setPriority(0);
                    System.out.println(seed.getTask_content());
                    seedService.insertSeed(seed);
                }
            }
        }
    }

    private void runTest2(){
        String file="D:/2018/itfin_url.xlsx";
        int a=0,b=0,c=0;
        List<JSONObject> jsonObjectList=null;
        JSONArray body=new JSONArray();
        try {
            jsonObjectList=seedService.readExcelFile(new File(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(jsonObjectList!=null){
            a=jsonObjectList.size();
            for(JSONObject jsonObject:jsonObjectList){
                String company=jsonObject.getString("1");
                String url=jsonObject.getString("2");
                List<Seed> seedList=seedService.selectSeed(url);
                if(seedList.size()>0){
                    b++;
                    jsonObject.put("3", "1");
                }else {
                    c++;
                    jsonObject.put("3", "0");
                }
                body.add(jsonObject);
            }
        }
        JSONArray head=new JSONArray();
        String[] arrhead={"0","1","2","3"};
        for(String str:arrhead){
            head.add(str);
        }
        //ExcelUtils.outFile(ExcelUtils.expExcel("itfin_url", head , body), "D:/2018/itfin_url2.xlsx");
        System.out.println("总大小：【"+a+"】     存在数目:【"+b+"】    不存在数目:【"+c+"】");
    }

    public void monitor(){}
}
