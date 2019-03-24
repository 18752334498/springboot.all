package com.yucong.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRepository extends JpaRepository<User, Long> {

}
