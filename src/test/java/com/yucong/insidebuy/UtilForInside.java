package com.yucong.insidebuy;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.yucong.insidebuy.entity.BuyLimit;
import com.yucong.insidebuy.entity.GoodsInfo;
import com.yucong.insidebuy.entity.GoodsType;

public class UtilForInside {

	public static void main(String[] args) {
		getGoodsInfo();
	}

	public static GoodsInfo getGoodsInfo() {
		GoodsInfo g = new GoodsInfo();
		BuyLimit buyLimit = new BuyLimit();
		GoodsType goodsType = new GoodsType();
		buyLimit.setGoodsModelNum("gghhjjkk");
		goodsType.setId(1l);

		// 必填
		g.setBuyLimit(buyLimit);
		g.setGoodsType(goodsType);
		g.setDescription("bigger than bigger");
		g.setGoodsName("honor10");
		g.setPicture("/aa/ss/magic.png");

		// 默认上架，即 1
		g.setStatus(1);

		// 选填
		g.setBrand("honor");
		g.setGoodsNum(1l);

		// 自定义
		g.setUpdateTime(new Date());

		System.out.println(JSON.toJSONString(g));

		return g;
	}

}
