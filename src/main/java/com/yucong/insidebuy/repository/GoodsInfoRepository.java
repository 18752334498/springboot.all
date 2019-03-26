package com.yucong.insidebuy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yucong.insidebuy.entity.GoodsInfo;

@Repository
public interface GoodsInfoRepository extends JpaRepository<GoodsInfo, Long> {


    @Query("select g from GoodsInfo g where g.status=1 order by g.goodsType.typeOrder, g.goodsNum, g.updateTime")
    List<GoodsInfo> findAllGoodsInfo();
}
