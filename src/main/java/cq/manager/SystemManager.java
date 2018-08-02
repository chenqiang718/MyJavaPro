package cq.manager;

import com.alibaba.fastjson.JSONObject;
import cq.Model.Seed;
import cq.Service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class SystemManager {
    @Autowired
    private SeedService seedService;
    public void init(){
//        runinsert();
        System.out.println("你好");
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

    public void monitor(){}
}
