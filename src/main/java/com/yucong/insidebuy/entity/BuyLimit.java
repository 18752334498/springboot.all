package com.yucong.insidebuy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 购买限制实体类
 * 
 * @Author: yucong
 * @Since: 2019年3月25日
 */
@Entity
@Table(name = "vwt_ins_buy_limit")
public class BuyLimit implements Serializable {

    private static final long serialVersionUID = -3140445298329857890L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vwt_ins_buy_limit_gen")
    @SequenceGenerator(name = "vwt_ins_buy_limit_gen", sequenceName = "vwt_ins_buy_limit_seq")
    private Long id;

    /** 商品机型编码 */
    @Column(name = "goods_model_num")
    private String goodsModelNum;

    /** 限购数量 */
    @Column(name = "limit_count")
    private Integer limitCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsModelNum() {
        return goodsModelNum;
    }

    public void setGoodsModelNum(String goodsModelNum) {
        this.goodsModelNum = goodsModelNum;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

}
