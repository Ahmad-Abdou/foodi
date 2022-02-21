package com.food.food.controller;

import com.food.food.emails.SendMail;
import com.food.food.entity.Order;
import com.food.food.model.ItemModel;
import com.food.food.model.OrderModel;
import com.food.food.service.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin("*")
public class OrderController {

    private static OrderService orderService;
    public static String email;
    public static String address;
    public static String fullName;
    public static LocalDate date;
    public static String orderNumber;
    public static String zipCode;
    public static double totalPrice;
    public static List<ItemModel> itemList;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public  ResponseEntity<OrderModel> create(@RequestBody OrderModel orderModel) throws NotFoundException {
       OrderModel orderModel1 = orderService.create(orderModel);
        email = orderModel1.getEmail();
        address = orderModel1.getAddress();
        zipCode = orderModel1.getZipCode();
        totalPrice = orderModel1.getTotalPrice();
        itemList = orderModel1.getItemList();
        fullName =orderModel1.getFirstName()+" "+orderModel1.getLastName();
        date=LocalDate.now();
        orderNumber = orderModel1.getOrderId();
        SendMail sendMail = new SendMail();
        sendMail.send();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderModel1);
    }
    @GetMapping()
    public ResponseEntity<List<OrderModel>> findByAll( ){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllOrders());
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> findById(@PathVariable("id") String id ) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrderById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) throws NotFoundException {
        orderService.deleteOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/phone")
    public ResponseEntity<OrderModel> findOrderByPhoneNumber(@RequestParam("number") String phoneNumber) throws NotFoundException {
        OrderModel orderModel = orderService.findOrderByPhoneNumber(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(orderModel);
    }
    @GetMapping("/items/bought")
    public ResponseEntity<List<OrderModel>> findMostBoughtItems(){
        List <OrderModel> listOfOrders =  orderService.findAllOrders();
        return null;
    }

}

