package com.food.food.repository;

import com.food.food.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findOrderByPhoneNumber(String phone);
}
