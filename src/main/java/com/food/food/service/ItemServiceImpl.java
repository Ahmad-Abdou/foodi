package com.food.food.service;

import com.food.food.entity.Discount;
import com.food.food.entity.Item;
import com.food.food.entity.Size;
import com.food.food.model.ItemModel;
import com.food.food.repository.ItemRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ModelMapper modelMapper;
    @Autowired
    public void setPizzaRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemModel create(ItemModel itemModel) {
        Item item = itemRepository.save(modelMapper.map(itemModel,Item.class));
        ItemModel pizzaResult = modelMapper.map(item,ItemModel.class);
        return pizzaResult;
    }

    @Override
    public ItemModel update(ItemModel itemModel) throws NotFoundException {
        Optional<Item> item = itemRepository.findById(itemModel.getId());
        if(item.isPresent()){
            Item savingItem = itemRepository.save(modelMapper.map(itemModel,Item.class));
            ItemModel pizzaResult = modelMapper.map(savingItem,ItemModel.class);
            return pizzaResult;
        }

        throw new NotFoundException("Pizza with that id does not exist");
    }

    @Override
    public List<ItemModel> findAll() {
        List<Item> itemList = new ArrayList<>();
        itemRepository.findAll().iterator().forEachRemaining(itemList::add);
        List<ItemModel> pizzaModelList = itemList.stream().map(item -> modelMapper.map(item,ItemModel.class)).collect(Collectors.toList());
        return pizzaModelList;
    }

    @Override
    public ItemModel findById(long id) throws NotFoundException {
        if(id == 0){
            throw new NotFoundException("Id cant be null");
        }
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            ItemModel itemResult = modelMapper.map(item.get(),ItemModel.class);
            return itemResult;
        }

        throw new NotFoundException("Item with that id does not exist");
    }

    @Override
    public boolean deleteById(long id) throws NotFoundException {
        if(id == 0){
            throw new NotFoundException("Id cant be null");
        }
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            itemRepository.deleteById(id);
            return true;
        }
        throw new NotFoundException("Item with that id does not exist");
    }

    @Override
    public List<ItemModel> findByPrice(double price) {
        List<Item> item = itemRepository.findItemByPrice(price);
        List<ItemModel>itemResult = item.stream().map(item1 -> modelMapper.map(item1,ItemModel.class)).collect(Collectors.toList());
        return itemResult;
    }

    @Override
    public List<ItemModel> findBySize(Size size) {
        List<Item> item = itemRepository.findItemBySize(size);
        List<ItemModel>itemResult = item.stream().map(item1 -> modelMapper.map(item1,ItemModel.class)).collect(Collectors.toList());
        return itemResult;
    }

    @Override
    public List<ItemModel> findByDiscount(Discount discount) {
        List<Item> item = itemRepository.findItemByDiscount(discount);
        List<ItemModel>itemResult = item.stream().map(item1 -> modelMapper.map(item1,ItemModel.class)).collect(Collectors.toList());
        return itemResult;
    }

    @Override
    public ItemModel findByName(String name) throws NotFoundException {
        if(name == ""){
            throw new NotFoundException("Name cant be empty");
        }
        Optional<Item> item = itemRepository.findItemByName(name.toLowerCase());
        if(item.isPresent()){
            ItemModel itemResult = modelMapper.map(item.get(),ItemModel.class);
            return itemResult;
        }
        throw new NotFoundException("Pizza not found");
    }
    @Override
    public ItemModel createDiscount(long id, double discount_amount) throws NotFoundException {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            if(discount_amount == 0){
                item.get().setDiscount(Discount.NO);
                double discount = Double.parseDouble (item.get().getDiscountAmount().substring(0,item.get().getDiscountAmount().length()-1));
                double price = item.get().getPrice();
                double result = price /discount * 100;
                DecimalFormat df = new DecimalFormat();
                item.get().setPrice((Double.parseDouble(df.format(result))));
            }
            else
            item.get().setDiscount(Discount.YES);
            item.get().setDiscountAmount(discount_amount+"%");
            double priceAfterDiscount = (item.get().getPrice() - (item.get().getPrice()*(discount_amount/100)));
            item.get().setPrice(priceAfterDiscount);
            itemRepository.save(item.get());
            ItemModel itemResult = modelMapper.map(item.get(),ItemModel.class);
            return itemResult;
        }
        throw new NotFoundException("Pizza not found");

    }
    @Override
    public ItemModel changePrice(long id, double price) throws NotFoundException {

        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            item.get().setPrice(price);
            itemRepository.save(item.get());
            ItemModel itemResult = modelMapper.map(item.get(),ItemModel.class);
            return itemResult;
        }
        throw new NotFoundException("Item not found");
    }
}
