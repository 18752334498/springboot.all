package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yucong.insidebuy.entity.GoodsType;

@Repository
public interface TypeRepository extends JpaRepository<GoodsType, Long> {


}
