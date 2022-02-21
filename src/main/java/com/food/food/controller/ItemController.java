package com.food.food.controller;

import com.food.food.entity.Discount;
import com.food.food.entity.Size;
import com.food.food.model.ItemModel;
import com.food.food.service.ItemService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

        private ItemService itemService;

        @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemModel> create(@RequestBody ItemModel itemModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(itemModel));
    }
    @GetMapping()
    public ResponseEntity<List<ItemModel>> findByAll( ){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ItemModel> findById(@PathVariable("id") long id ) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findById(id));
    }
    @GetMapping("/price")
    public ResponseEntity<List<ItemModel>> findByPrice(@RequestParam ("price")double price){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findByPrice(price));
    }
    @GetMapping("/discount")
    public ResponseEntity<List<ItemModel>> findByDiscount(@RequestParam("discount") Discount discount){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findByDiscount(discount));
    }
    @GetMapping("/size")
    public ResponseEntity<List<ItemModel>> findBySize(@RequestParam("size") Size size){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findBySize(size));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) throws NotFoundException {
            itemService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{name}")
    public ResponseEntity<ItemModel> findByName(@RequestParam("name") String name) throws NotFoundException {
        ItemModel pizzaModel = itemService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(pizzaModel);
    }
    @PostMapping("/discount/{id}")
    public ResponseEntity<ItemModel> createDiscount(@PathVariable("id") long id , @RequestParam("discount")int discount_amount) throws NotFoundException {
        ItemModel pizzaModelResult = itemService.createDiscount(id,discount_amount);
        return ResponseEntity.status(HttpStatus.OK).body(pizzaModelResult);

    }
    @PostMapping("/price/{id}")
    public ResponseEntity<ItemModel> changePrice(@PathVariable("id") long id , @RequestParam("price")double price) throws NotFoundException {
        ItemModel pizzaModelResult = itemService.changePrice(id,price);
        return ResponseEntity.status(HttpStatus.OK).body(pizzaModelResult);

    }
    @PostMapping("/upload")
    public ResponseEntity<ItemModel> uploadFile(@RequestParam("file") MultipartFile multipartFile, @ModelAttribute ItemModel itemModel){
            fileConvert(multipartFile, itemModel);
        return ResponseEntity.status(HttpStatus.OK).body(itemService.create(itemModel));
    }

    public static void fileConvert(@RequestParam("file") MultipartFile multipartFile, @ModelAttribute ItemModel itemModel) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path path = Paths.get("src/main/resources/static/images/"+fileName);
        try{
            Files.copy(multipartFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            itemModel.setImage(multipartFile.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

