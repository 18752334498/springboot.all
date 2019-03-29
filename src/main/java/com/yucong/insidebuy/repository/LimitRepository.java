package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yucong.insidebuy.entity.BuyLimit;

@Repository
public interface LimitRepository extends JpaRepository<BuyLimit, Long> {


}
