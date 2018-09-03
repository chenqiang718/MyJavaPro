package cq.Dao;


import cq.Model.Seed;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeedDao {
    //返回总记录数
    int countSeed();

    /**
     * 根据remark筛选seed,若remark为空，则筛选全部
     * @param content
     * @return 筛选数据
     * @author chenqiang
     */
    List<Seed> querySeed(@Param("content") String content);

    /**
     * 插入一条新的seed
     *
     * @param seed
     * @return 受影响的行数
     * @author chenqiang
     */
    Integer insert(@Param("seed") Seed seed);

    /**
     * 更改seed的值
     *
     * @param seed
     * @return 受影响的行数
     * @author chenqiang
     */
    int update(@Param("seed") Seed seed);

}
