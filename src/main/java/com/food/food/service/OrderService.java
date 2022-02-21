package com.food.food.service;


import com.food.food.model.OrderModel;
import javassist.NotFoundException;

import java.util.List;

public interface OrderService {

    OrderModel create (OrderModel orderModel) throws NotFoundException;
    List<OrderModel> findAllOrders();
    OrderModel findOrderById(String id) throws NotFoundException;
    OrderModel findOrderByPhoneNumber(String phone) throws NotFoundException;
    boolean deleteOrderById(String id) throws NotFoundException;



}
