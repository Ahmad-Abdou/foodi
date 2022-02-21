package com.food.food.service;


import com.food.food.entity.Order;
import com.food.food.model.OrderModel;
import com.food.food.repository.OrderRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;


    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public OrderModel create(OrderModel orderModel) throws NotFoundException {

        List<Double> price = orderModel.getItemList().stream().map(itemModel -> itemModel.getPrice() * itemModel.getQuantity()).collect(Collectors.toList());
        double sum = 0;
        for(int i = 0 ; i <price.size(); i ++){
            Double [] priceArray = price.toArray(new Double[0]) ;
            sum = sum+ priceArray[i] ;
        }
        LocalDate date =LocalDate.now();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        orderModel.getItemList().forEach(itemModel -> itemModel.setImage(null));
        orderModel.setTotalPrice(Double.parseDouble(df.format(sum)));
        orderModel.setDate(date);
        Order order = orderRepository.save(modelMapper.map(orderModel,Order.class));
        OrderModel orderModel1 = modelMapper.map(order,OrderModel.class);
        orderModel1.setDate(LocalDate.now());
        orderModel1.setItemList(orderModel.getItemList());
        return orderModel1;
    }

    @Override
    public List<OrderModel> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().iterator().forEachRemaining(orders::add);
        List<OrderModel> orderModelList = orders.stream().map(order -> modelMapper.map(order,OrderModel.class)).collect(Collectors.toList());
        return orderModelList;
    }

    @Override
    public OrderModel findOrderById(String id) throws NotFoundException {
        if(id == ""){
            throw new NotFoundException("Id cant be null");
        }
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            OrderModel orderModel = modelMapper.map(order.get(),OrderModel.class);
            return orderModel;
        }
        throw new NotFoundException("Order with that id does not exist");
    }

    @Override
    public OrderModel findOrderByPhoneNumber(String phone) throws NotFoundException {
        if(phone == ""){
            throw new NotFoundException("phone number cant be null");
        }
        Optional<Order> order = orderRepository.findOrderByPhoneNumber(phone);
        if(order.isPresent()){
            OrderModel orderModel = modelMapper.map(order.get(),OrderModel.class);
            return orderModel;
        }
        throw new NotFoundException("Order with that phone number does not exist");
    }

    @Override
    public boolean deleteOrderById(String id) throws NotFoundException {
        if(id == ""){
            throw new NotFoundException("Id cant be null");
        }
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            orderRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException("Order with that id does not exist");
    }


}
