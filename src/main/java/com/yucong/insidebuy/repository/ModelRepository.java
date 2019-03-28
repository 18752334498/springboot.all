package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yucong.insidebuy.entity.GoodsModel;

@Repository
public interface ModelRepository extends JpaRepository<GoodsModel, Long> {

	@Modifying
	@Transactional
	@Query("update GoodsModel m set m.inventory = (m.inventory + :goodsCount) where id = :goodsModelId")
	int updateGoodsCountById(@Param("goodsModelId") Long goodsModelId, @Param("goodsCount") Integer goodsCount);


}
