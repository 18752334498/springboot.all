package com.yucong.insidebuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yucong.insidebuy.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query(nativeQuery = true, value = "select count(*) from vwt_ins_shopping_cart")
    public Integer findNumOfShoppingCart();


}
