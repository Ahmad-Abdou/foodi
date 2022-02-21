package com.food.food.model;

import com.food.food.entity.Discount;
import com.food.food.entity.Size;
import com.food.food.entity.Type;
import lombok.Data;

import javax.persistence.Lob;

@Data
public class ItemModel {
    private long id;
    private String name;
    private double price;
    private Type type;
    private Size size;
    private int quantity = 1;
    private String [] ingredients;
    private Discount discount = Discount.NO;
    private String discountAmount = "0";
    private byte[] image;
    private String extra_info;

}
