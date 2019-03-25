package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yucong.insidebuy.entity.Activity;

@Repository
public interface BuyRepository extends JpaRepository<Activity, Long> {

}
