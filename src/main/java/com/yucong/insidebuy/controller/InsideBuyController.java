package com.yucong.insidebuy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yucong.insidebuy.service.GoodsInfoService;
import com.yucong.insidebuy.service.GoodsModelService;

@RestController
@RequestMapping("buy")
public class InsideBuyController {

	@Autowired
	private GoodsModelService goodsModelService;
	@Autowired
	private GoodsInfoService goodsInfoService;

	@RequestMapping("transactionl")
	public String test_transactional() {
		try {
			goodsModelService.test_transactional();
		} catch (Exception e) {
			System.out.println("===========================1" + e.getMessage());
			System.out.println("===========================2" + e.getCause());
			e.getCause();
		}
		return "success";
	}

	@RequestMapping("update")
	public String test_updateGoodsForManager() {
		goodsInfoService.updateGoodsForManager();
		return "success";
	}


}
