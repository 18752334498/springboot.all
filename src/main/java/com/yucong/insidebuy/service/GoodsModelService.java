package com.yucong.insidebuy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yucong.insidebuy.repository.GoodsModelRepository;

@Component
public class GoodsModelService {

	@Autowired
	private GoodsModelRepository goodsModelRepository;

	@Transactional
	public void test_transactional() throws Exception {
		System.out.println("============start=============");

		// 库存 -2
		int count1 = goodsModelRepository.updateInventoryById(2, 1l);
		System.out.println("更新 count1 ：" + count1);

		// 库存 +2
		int count2 = goodsModelRepository.updateGoodsCountById(2l, 2);
		System.out.println("更新 count12：" + count2);

		System.out.println("============end=============");
//		throw new Exception("自定义异常");
		int a = 1 / 0;
	}

}
