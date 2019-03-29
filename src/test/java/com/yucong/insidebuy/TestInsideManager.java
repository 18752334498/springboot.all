package com.yucong.insidebuy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yucong.App;
import com.yucong.insidebuy.entity.GoodsInfo;
import com.yucong.insidebuy.repository.ActivityRepository;
import com.yucong.insidebuy.repository.GoodsInfoRepository;
import com.yucong.insidebuy.repository.GoodsModelRepository;
import com.yucong.insidebuy.repository.OrderRepository;
import com.yucong.insidebuy.repository.ShoppingCartRepository;
import com.yucong.insidebuy.repository.TypeRepository;
import com.yucong.insidebuy.service.GoodsDetailService;
import com.yucong.insidebuy.service.GoodsInfoService;
import com.yucong.insidebuy.service.OrderService;
import com.yucong.insidebuy.service.ShoppingCartService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestInsideManager {

	@Autowired
	private GoodsInfoRepository goodsInfoRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private GoodsInfoService goodsInfoService;
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private GoodsDetailService goodsDetailService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private GoodsModelRepository goodsModelRepository;

	@Test
	public void test_finfAllForManager() {
		Page<GoodsInfo> page = goodsInfoRepository.findAllGoodsInfo(PageRequest.of(0, 2));

		Object json = JSON.toJSON(page.getContent());
		System.out.println(json.toString());
	}

	@Test
	public void test_saveGoodsInfoForManager() {
		goodsInfoRepository.save(UtilForInside.getGoodsInfo());
	}

	@Test
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Rollback(false)
	public void test_updateGoodsForManager() {
		GoodsInfo goodsInfo = UtilForInside.getGoodsInfo();
		goodsInfo.setId(52l);
		goodsInfo.setGoodsNum(3543543l);

		goodsInfoRepository.save(goodsInfo);
	}

	@Test
	public void test_getGoodsInfoForManager() {
		GoodsInfo goodsInfo = goodsInfoRepository.findById(52l).get();
//		System.out.println(JSON.toJSONString(goodsInfo, SerializerFeature.UseISO8601DateFormat));
		System.out.println(JSON.toJSONString(goodsInfo));
	}

}
