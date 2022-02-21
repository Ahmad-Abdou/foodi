package com.food.food.model;
import com.food.food.entity.PaymentStatus;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderModel {
    private String orderId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private String zipCode;
    private double totalPrice;
    private boolean delivery;
    private List<ItemModel> itemList;
    private PaymentStatus paymentStatus=  PaymentStatus.PENDING;
    private LocalDate Date;
}
