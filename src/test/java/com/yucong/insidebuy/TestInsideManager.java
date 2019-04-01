package com.yucong.insidebuy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yucong.App;
import com.yucong.insidebuy.entity.Activity;
import com.yucong.insidebuy.entity.GoodsInfo;
import com.yucong.insidebuy.entity.GoodsModel;
import com.yucong.insidebuy.entity.GoodsType;
import com.yucong.insidebuy.entity.Order;
import com.yucong.insidebuy.repository.ActivityRepository;
import com.yucong.insidebuy.repository.GoodsInfoRepository;
import com.yucong.insidebuy.repository.GoodsModelRepository;
import com.yucong.insidebuy.repository.GoodsTypeRepository;
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
	@Autowired
	private GoodsTypeRepository goodsTypeRepository;

	@Test
	public void test_finfAllForManager() {
		Page<GoodsInfo> page = goodsInfoRepository.findAllGoodsInfo(PageRequest.of(0, 2));

		JSONObject result = new JSONObject();
		result.put("data", page.getContent());
		result.put("nums", page.getTotalPages());
		System.out.println(JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));
	}

	@Test
	public void test_saveGoodsInfoForManager() {
		goodsInfoRepository.save(UtilForInside.getGoodsInfo());
	}

	@Test
	@Transactional
	@Rollback(false)
	public void test_updateGoodsForManager() {
		GoodsInfo goodsInfo = UtilForInside.getGoodsInfo();
		goodsInfo.setId(52l);
		goodsInfo.setGoodsNum(100l);
		goodsInfo.setLimitCount(100);

		goodsInfoRepository.save(goodsInfo);
	}

	@Test
	public void test_getGoodsInfoForManager() {
		GoodsInfo goodsInfo = goodsInfoRepository.findById(1l).get();
//		System.out.println(JSON.toJSONString(goodsInfo, SerializerFeature.UseISO8601DateFormat));
		System.out.println(JSON.toJSONString(goodsInfo));
	}

	@Test
	@Transactional
	public void test_deleteGoodsForManager() {
		List<Long> ids = new ArrayList<>();
		ids.add(10l);
		int count = orderRepository.findAllByGoodsInfoId(10l);
		System.out.println(count);
		if (count > 0) {
			System.out.println("客户有订单，不能删");
		} else {
			goodsInfoRepository.deleteById(10l);
			System.out.println("商品删除成功");
		}
		int num = goodsModelRepository.deleteGoodsModelByGoodsInfoId(10l);
		System.out.println("删除了型号个数： " + num);
	}

	@Test
	public void test_finfAllTypeForManager() {
		Page<GoodsType> page = goodsTypeRepository.findAll(PageRequest.of(0, 2, Direction.ASC, "typeOrder"));

		JSONObject result = new JSONObject();
		result.put("data", page.getContent());
		result.put("nums", page.getTotalPages());
		System.out.println(result.toString());
	}

	@Test
	public void test_getActivityDetail() {

		// 活动查询
		List<Activity> list = activityRepository.findAll();
		List<Map<String, Object>> buyLimit = goodsInfoService.findModelNumAndLimitCount();

		// 结果集封装
		JSONObject jsonObject = new JSONObject();
		if (CollectionUtils.isEmpty(list)) {
			jsonObject.put("activity", "");
		} else {
			jsonObject.put("activity", list.get(0));
		}
		jsonObject.put("buyLimit", buyLimit);

		System.out.println(jsonObject.toString());
	}

	@Test
	public void updateActivity() {

		String aa = "{\"buyLimit\":[{\"goods_info_id\":1,\"model_num\":\"aaa\",\"limit_count\":8},{\"goods_info_id\":2,\"model_num\":\"bbb\",\"limit_count\":9}],"
				+ "\"activity\":{\"endIntroduce\":\"Thank for your money!\",\"endTime\":1553506881000,\"limitTotal\":10,\"startIntroduce\":\"Activity now is starting!\",\"startTime\":1555062088000}}";

		Activity activity = checkParamForAcivity(aa);
		activity.setLimitTotal(20);
		Activity a = activityRepository.save(activity);
		System.out.println(JSON.toJSONString(a));

	}

	@Test
	public void test_changeBuyLimit() {
		GoodsInfo goodsInfo = goodsInfoRepository.findByModelNum("uuu");
		if (goodsInfo == null) {
			System.out.println("机型编码不存在 ");
		}
		goodsInfo.setLimitCount(333);

		// 更新成功返回整个对象
		GoodsInfo save = goodsInfoRepository.save(goodsInfo);

		System.out.println(JSON.toJSONString(save));
	}

	@Test
	public void test_getOrderList() {
//		JSONObject jsonObject = orderService.findOrderListForManager();

		Page<Order> page = orderRepository.findAll(PageRequest.of(0, 2));

		List<Order> list = page.getContent();
		for (Order order : list) {
			Long goods_model_id = order.getGoodsModel().getId();

			// 临时队列
			List<GoodsModel> temGoodsModelList = new ArrayList<>();
			GoodsInfo temGoodsInfo = new GoodsInfo();
			GoodsModel temGoodsModel = new GoodsModel();

			List<GoodsModel> goodsModels = order.getGoodsInfo().getGoodsModels();
			for (GoodsModel m : goodsModels) {
				if (m.getId().equals(goods_model_id)) {
					temGoodsModel.setDiscountPrice(m.getDiscountPrice());
					temGoodsModel.setGoodsModel(m.getGoodsModel());

					temGoodsModelList.add(temGoodsModel);

					temGoodsInfo.setGoodsName(order.getGoodsInfo().getGoodsName());
					temGoodsInfo.setGoodsModels(temGoodsModelList);
					break;
				}
			}
			order.setGoodsInfo(temGoodsInfo);
			order.setGoodsModel(null);
		}

		JSONObject result = new JSONObject();
		result.put("data", list);
		result.put("nums", page.getTotalPages());
		System.out.println(JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));

	}

	@Test
	public void test_deleteAll() {
		activityRepository.deleteAll();
	}
	
	@Test
	public void test_saveModelForManager() {
		GoodsModel model = new GoodsModel();
		model.setDiscountPrice(666d);
		model.setMarketPrice(888d);
		model.setGoodsModel("xiaomi9_yellow");
		model.setInventory(33);

		GoodsModel save = goodsModelRepository.save(model);

		System.out.println(JSON.toJSONString(save));

//		Optional<GoodsModel> goodsModel = goodsModelRepository.findById(1l);
//		System.out.println(JSON.toJSONString(goodsModel.get()));
	}

	@Test
	public void test_deleteModelForManager() {
		goodsModelRepository.deleteById(10l);

	}

	/**
	 * <li>对 GoodsType 中的参数做校验</li>
	 * 
	 * @param goodsType
	 * @return
	 */
	private Activity checkParamForAcivity(String request_body) {
		Activity a;
		try {
			JSONObject jsonObject = JSONObject.parseObject(request_body);
			a = JSON.parseObject(jsonObject.getString("activity"), Activity.class);
		} catch (Exception e) {
			return null;
		}

		// 4个非空参数
		if (StringUtils.isEmpty(a.getStartTime()) || StringUtils.isEmpty(a.getEndTime())
				|| StringUtils.isEmpty(a.getStartIntroduce()) || StringUtils.isEmpty(a.getEndIntroduce())) {
			return null;
		}

		// 限购总数

		// 新增操作不设置ID，更新操作默认有ID
		return a;
	}

}
