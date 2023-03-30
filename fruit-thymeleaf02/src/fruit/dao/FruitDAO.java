package fruit.dao;

import fruit.pojo.Fruit;

import java.util.List;

/**
 * @title:FruitDAO
 * @Author Ice
 * @Date: 2022/4/13 23:45
 * @Version 1.0
 */

public interface FruitDAO {
    //获取所有的列表信息
    List<Fruit> getFruitList(String keyword,Integer pageNo);

    //根据 fid 获取指定的水果库存信息
    Fruit getFruitById(Integer fid);

//    修改指定的库存记录
    void updateFruit(Fruit fruit);

//    根据 fid 删除指定的库存记录
    void delFruit(Integer fid);

//    添加方法
    void addFruit(Fruit fruit);

//    查询库存总记录条数
    int getFruitCount(String keyword);


}