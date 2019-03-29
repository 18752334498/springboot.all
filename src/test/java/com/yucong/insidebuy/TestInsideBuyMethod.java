package com.yucong.insidebuy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yucong.App;
import com.yucong.insidebuy.repository.GoodsModelRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestInsideBuyMethod {

	@Autowired
	private GoodsModelRepository goodsModelRepository;

	@Test
	@Transactional
	@Rollback(value = false)
	public void test_transactional() {
		//
		try {
			// 购买商品，减去库存
			int count1 = goodsModelRepository.updateInventoryById(2, 1l);
			System.out.println("更新 count1 ：" + count1);

			// 取消订单，返回库存
			int count2 = goodsModelRepository.updateGoodsCountById(2l, 2);
			System.out.println("更新 count12：" + count2);

			throw new RuntimeException("自定义异常");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
