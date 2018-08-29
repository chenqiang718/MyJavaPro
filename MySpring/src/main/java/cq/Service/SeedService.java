package cq.Service;

import com.alibaba.fastjson.JSONObject;
import cq.Dao.SeedDao;
import cq.Model.Seed;
import cq.common.ReadExcel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service("seedService")
public class SeedService {

    @Resource
    private SeedDao seedDao;

    public Integer countSeed() {
        return seedDao.countSeed();
    }

    public List<Seed> selectSeed(String content) {
        return seedDao.querySeed(content);
    }

    //media_cd,task_type,task_content必填，其它可不填
    public int insertSeed(Seed seed) {
        return seedDao.insert(seed);
    }

    //通过ID查找，改变一个seed的值
    public int updateSeed(Seed seed) {
        return seedDao.update(seed);
    }

    /**
     * @param file
     * @return result
     * -1:上传失败 0:上传成功 >1:第几行数据有重复
     */
    public List<JSONObject> readExcelFile(File file) throws Exception {
        ReadExcel readExcel=null;
        try {
            readExcel=new ReadExcel(file, false);
            List<JSONObject> jsonObjectList = readExcel.readExcel(0, 0);
            return jsonObjectList;
        }finally {
            if(readExcel!=null){
                readExcel.close();
            }
        }

    }
}
