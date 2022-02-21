package com.food.food.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Item {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String name;
        private double price;
        private Size size;
        private Type type;
        private Discount discount;
        private String discountAmount;
        private int quantity = 1;
        private String [] ingredients;
        @Lob
        private byte[] image;
        private String extra_info;


}
