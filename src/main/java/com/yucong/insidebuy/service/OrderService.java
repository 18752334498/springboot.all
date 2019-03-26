package com.yucong.insidebuy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.royasoft.core.tools.common.dao.QueryUtils;

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

}
