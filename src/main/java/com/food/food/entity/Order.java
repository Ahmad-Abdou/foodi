package com.food.food.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_id")
    private String id;
    private String firstName;
    private String lastName;
//    @Column(unique = true)
    private String phoneNumber;
    private String address;
    private String email;
    private double totalPrice;
    private boolean delivery;
    private String zipCode;
    @ManyToMany
    private List<Item> item;
    private PaymentStatus paymentStatus;
    private LocalDate orderDate;


}
