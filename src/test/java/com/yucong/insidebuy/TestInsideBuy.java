package com.yucong.insidebuy;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yucong.App;
import com.yucong.insidebuy.entity.GoodsType;
import com.yucong.insidebuy.repository.TypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestInsideBuy {
    
    @Autowired
    private TypeRepository repo;

    @Test
    public void test_findAll() {
        List<GoodsType> list = repo.findAll();
        for (GoodsType goodsType : list) {
            System.out.println(goodsType);
        }
    }

    @Test
    public void test_findAllAndSort() {
        List<GoodsType> list = repo.findAllAndSort();
        for (GoodsType goodsType : list) {
            System.out.println(goodsType);
        }
    }

}
