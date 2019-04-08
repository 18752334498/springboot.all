package com.yucong.insidebuy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yucong.util.QueryUtils;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager manager;

    /**
     * <span>我的订单接口</span>
     * 
     * @Author: yucong
     * @Since: 2019年3月26日
     */
    public List<Map<String, Object>> findOrderList() {

        StringBuffer sql = new StringBuffer();
        sql.append("select i.description, i.goods_name, i.model_num, i.picture, i.status, ");
        sql.append("m.discount_price, m.goods_model, m.inventory, ");
        sql.append("o.address, o.business_hall, o.create_time, o.goods_count, o.order_group, o.phone_id, o.phone_num, o.username ");
        sql.append("from vwt_ins_order o ");
        sql.append("join vwt_ins_goods_info i on o.goods_info_id = i.id ");
        sql.append("join vwt_ins_goods_model m on o.goods_model_id = m.id ");
        sql.append("where o.phone_id = :phone_id order by o.create_time");

        Map<String, Object> map = new HashMap<>();
        map.put("phone_id", "18752334498");

        return QueryUtils.queryForMap(manager, sql.toString(), map);
    }

    // 查出各种限制条件
    public Map<String, Object> queryAllLimitCount(Long goodsInfoId, Long goodsModelId) {

        StringBuffer sql = new StringBuffer();

        sql.append("select ");
        sql.append("SUM(o.goods_count) as orderedCount, ");
        sql.append("(select a.limit_total as actCount from vwt_ins_activity a) as actLimitCount, ");
        sql.append("(select m.inventory from vwt_ins_goods_model m where m.id = :goodsModelId) as inventory, ");
        sql.append("(select i.limit_count from vwt_ins_goods_info i where i.id = :goodsInfoId) modelLimitCount ");
        sql.append("from vwt_ins_order o where o.phone_id = 18752334498 and o.goods_info_id = 1");

        Map<String, Object> map = new HashMap<>();
        map.put("goodsInfoId", goodsInfoId);
        map.put("goodsModelId", goodsModelId);
        Map<String, Object> singleForMap = QueryUtils.querySingleForMap(manager, sql.toString(), map);
        System.out.println(singleForMap);
        return singleForMap;
    }

    public JSONObject findOrderListForManager() {

        StringBuffer sql = new StringBuffer();
        sql.append("select o.id, o.address, o.business_hall, o.department,o.employee_num, ");
        sql.append("o.goods_count,o.order_num,o.phone_num,o.username,o.create_time, ");
        sql.append("i.goods_name,m.discount_price,m.goods_model ");
        sql.append("from vwt_ins_order o ");
        sql.append("join vwt_ins_goods_info i on i.id = o.goods_info_id ");
        sql.append("join vwt_ins_goods_model m on m.id = o.goods_model_id");
        Page<Map<String, Object>> page = QueryUtils.queryForMap(manager, sql.toString(), new HashMap<>(), PageRequest.of(0, 2));

        JSONObject result = new JSONObject();
        result.put("data", page.getContent());
        result.put("nums", page.getTotalPages());
        System.out.println(JSON.toJSONString(result));
        return result;
    }

}
