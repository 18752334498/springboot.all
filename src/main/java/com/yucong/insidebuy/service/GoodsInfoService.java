package com.yucong.insidebuy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.royasoft.core.tools.common.dao.QueryUtils;

@Service
public class GoodsInfoService {

    @PersistenceContext
    private EntityManager manager;

    public List<Map<String, Object>> findGoodsList() {
        StringBuffer sql = new StringBuffer();
        sql.append("select type.goods_type, ");
		sql.append("info.goods_name,info.description, info.picture, info.model_num, info.id as goods_info_id, ");
        sql.append("model.discount_price, model.market_price, ");
        sql.append("buy.limit_count ");
        sql.append("from vwt_ins_goods_info info ");
        sql.append("join vwt_ins_goods_type type on info.goods_type_id = type.id ");
        sql.append("join vwt_ins_goods_model model on model.goods_info_id = info.id ");
        sql.append("left join vwt_ins_buy_limit buy on buy.goods_model_num = info.model_num ");
        sql.append("where info.status = 1 order by type.type_order, info.goods_num, info.update_time");

        return QueryUtils.queryForMap(manager, sql.toString(), new HashMap<>());
    }

    public void countshoppingCart() {

    }

}
