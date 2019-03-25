package com.yucong.insidebuy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yucong.App;
import com.yucong.insidebuy.repository.BuyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestInsideBuy {
    
    @Autowired
    private BuyRepository repo;

    @Test
    public void test1() {
        repo.findAll();
    }

}
