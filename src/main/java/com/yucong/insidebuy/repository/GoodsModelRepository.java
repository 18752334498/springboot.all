package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yucong.insidebuy.entity.GoodsModel;

@Repository
public interface GoodsModelRepository extends JpaRepository<GoodsModel, Long> {


}
