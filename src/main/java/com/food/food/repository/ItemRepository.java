package com.food.food.repository;

import com.food.food.entity.Discount;
import com.food.food.entity.Item;
import com.food.food.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findItemByPrice(double price);

    List<Item> findItemByDiscount(Discount discount);

    List<Item> findItemBySize(Size size);

    Optional<Item> findItemByName(String name);



}
