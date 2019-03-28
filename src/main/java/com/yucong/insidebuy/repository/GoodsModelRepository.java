package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yucong.insidebuy.entity.GoodsModel;

@Repository
public interface GoodsModelRepository extends JpaRepository<GoodsModel, Long> {


	@Modifying
	@Query("update GoodsModel g set g.inventory = (g.inventory - :preBuyCount) where g.inventory >= :preBuyCount and g.id = :goodsModelId")
	int updateInventoryById(@Param("preBuyCount") Integer preBuyCount, @Param("goodsModelId") Long goodsModelId);
}
