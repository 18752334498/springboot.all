package com.yucong.insidebuy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class BuyLimit {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vwt_ins_buy_limit_gen")
    @SequenceGenerator(name = "vwt_ins_buy_limit_gen", sequenceName = "vwt_ins_buy_limit_seq")
    private Long id;

    /** 商品机型编码 */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "goods_model_num", referencedColumnName = "model_num")
    private GoodsInfo goodsInfo;

    /** 限购数量 */
    @Column(name = "limit_count")
    private Integer limitCount;

    /** 每个用户限购总数量 */
    @Column(name = "limit_total")
    private Integer limitTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Integer getLimitTotal() {
        return limitTotal;
    }

    public void setLimitTotal(Integer limitTotal) {
        this.limitTotal = limitTotal;
    }

}
