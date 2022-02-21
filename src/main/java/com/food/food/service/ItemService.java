package com.food.food.service;
import com.food.food.entity.Discount;
import com.food.food.entity.Size;
import com.food.food.model.ItemModel;
import javassist.NotFoundException;

import java.util.List;

public interface ItemService {

    ItemModel create (ItemModel itemModel);
    ItemModel update (ItemModel itemModel) throws NotFoundException;
    List<ItemModel> findAll();
    ItemModel findById(long id) throws NotFoundException;
    boolean deleteById(long id) throws NotFoundException;
    List<ItemModel> findByPrice(double price);
    List<ItemModel> findBySize(Size size);
    List<ItemModel> findByDiscount(Discount discount);
    ItemModel findByName(String name) throws NotFoundException;
    ItemModel createDiscount(long id, double discount_amount) throws NotFoundException;
    ItemModel changePrice (long id , double price) throws NotFoundException;

}
