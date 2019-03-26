package com.yucong.insidebuy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.yucong.App;
import com.yucong.insidebuy.entity.Activity;
import com.yucong.insidebuy.entity.GoodsInfo;
import com.yucong.insidebuy.entity.GoodsModel;
import com.yucong.insidebuy.entity.ShoppingCart;
import com.yucong.insidebuy.repository.ActivityRepository;
import com.yucong.insidebuy.repository.GoodsInfoRepository;
import com.yucong.insidebuy.repository.OrderRepository;
import com.yucong.insidebuy.repository.ShoppingCartRepository;
import com.yucong.insidebuy.repository.TypeRepository;
import com.yucong.insidebuy.service.GoodsDetailService;
import com.yucong.insidebuy.service.GoodsInfoService;
import com.yucong.insidebuy.service.OrderService;
import com.yucong.insidebuy.service.ShoppingCartService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestInsideBuy {
    
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private GoodsInfoRepository goodsInfoRepository;
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

    @Test
    public void test_findAll() {
        typeRepository.findAll();
    }

    /**
     * <span>内购活动首页接口</span>
     * 
     * @Author: yucong
     * @Since: 2019年3月26日
     */
    @Test
    public void test_findGoodsList() {

        List<Map<String, Object>> list = goodsInfoService.findGoodsList();

        List<String> goodsNames = list.stream().map(m -> String.valueOf(m.get("goods_name"))).distinct().collect(Collectors.toList());
        System.out.println(goodsNames);
        List<Map<String, Object>> newList = new ArrayList<>();
        for (String goodsName : goodsNames) {
            Map<String, Object> map = list.stream().filter(m -> goodsName.equals(m.get("goods_name"))).collect(Collectors.toList()).get(0);
            newList.add(map);
        }

        List<Object> data = new ArrayList<>();
        List<String> goodsTypes = list.stream().map(m -> String.valueOf(m.get("goods_type"))).distinct().collect(Collectors.toList());
        System.out.println(goodsTypes);
        for (String goodsType : goodsTypes) {
            List<Map<String, Object>> collect = newList.stream().filter(m -> goodsType.equals(m.get("goods_type"))).collect(Collectors.toList());
            data.add(collect);
        }


        Map<String, Object> result = new HashMap<>();

        result.put("data", data);

        Integer num = shoppingCartRepository.findNumOfShoppingCart();
        System.out.println("购物车数量是： " + num);
        result.put("shoppingCart", num);

        Activity activity = activityRepository.findAll().get(0);
        System.out.println("活动说明： " + activity.getStartIntroduce());
        result.put("activity", activity.getStartIntroduce());


        System.out.println(JSON.toJSON(result));
    }


    /**
     * <span>在列表页面点击某个商品产看详情，此页面有加入购物车功能</span>
     * 
     * @Author: yucong
     * @Since: 2019年3月26日
     */
    @Test
    public void test_findGoodsDetail() {
        List<Map<String, Object>> list = goodsDetailService.findGoodsDetail();
        List<String> goodsModel = list.stream().map(m -> String.valueOf(m.get("goods_model"))).collect(Collectors.toList());

        List<Object> data = new ArrayList<>();
        data.add(list);
        data.add(goodsModel);
        System.out.println(JSON.toJSONString(data));
    }

    /**
     * <span>在购物车添加商品</span>
     * 
     * @Author: yucong
     * @Since: 2019年3月26日
     */
    @Test
    public void test_saveShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setId(666l);
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setId(666l);

        shoppingCart.setGoodsCount(666);
        shoppingCart.setPhoneId(18752334499l);
        shoppingCart.setGoodsInfo(goodsInfo);
        shoppingCart.setGoodsModel(goodsModel);

        shoppingCartRepository.save(shoppingCart);
    }

    /**
     * <span>购物车接口</span>
     * 
     * @Author: yucong
     * @Since: 2019年3月26日
     */
    @Test
    public void test_findShoppingCartList() {
        List<Map<String, Object>> list = shoppingCartService.findShoppingCartList();
        System.out.println(JSON.toJSONString(list));
    }


    /**
     * <span>我的订单接口</span>
     * 
     * @Author: yucong
     * @Since: 2019年3月26日
     */
    @Test
    public void test_findfindOrderList() {
        List<Map<String, Object>> list = orderService.findOrderList();
        List<String> orderGroups = list.stream().map(m -> String.valueOf(m.get("order_group"))).distinct().collect(Collectors.toList());

        List<Object> result = new ArrayList<>();
        for (String orderGroup : orderGroups) {
            result.add(list.stream().filter(m -> orderGroup.equals(m.get("order_group"))).collect(Collectors.toList()));
        }
        System.out.println(JSON.toJSONString(result));
    }

    /**
     * <span>根据phoneId和orderGroup删除订单</span>
     * 
     * @Author: yucong
     * @Since: 2019年3月26日
     */
    @Test
    public void test_deleteByPhoneIdAndOrderGroup() {
        Long phoneId = 18752334498l;
        String orderGroup = "xxoo";
        int count = orderRepository.deleteByPhoneIdAndOrderGroup(phoneId, orderGroup);
        System.out.println(JSON.toJSONString("成功删除了： " + count));
    }


}
