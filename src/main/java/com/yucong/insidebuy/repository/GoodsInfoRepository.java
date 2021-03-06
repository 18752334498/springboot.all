package com.yucong.insidebuy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yucong.insidebuy.entity.GoodsInfo;

public interface GoodsInfoRepository extends JpaRepository<GoodsInfo, Long> {


	@Query("select g from GoodsInfo g order by g.goodsType.typeOrder, g.goodsNum, g.updateTime")
	Page<GoodsInfo> findAllGoodsInfo(Pageable pageable);

	/*******************/

}
