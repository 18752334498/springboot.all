package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yucong.insidebuy.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


	@Modifying
    @Transactional
	@Query(nativeQuery = true, value = "delete from vwt_ins_order where order_group = :orderGroup")
	int deleteByOrderGroup(@Param("orderGroup") String orderGroup);

}
