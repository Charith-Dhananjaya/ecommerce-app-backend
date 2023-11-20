package com.emart.item.controller;

import com.emart.item.dto.ItemRequest;
import com.emart.item.dto.ItemResponse;
import com.emart.item.dto.LineItemResponse;
import com.emart.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@RequestBody ItemRequest itemRequest) {
        itemService.createItem(itemRequest);
    }

    @GetMapping("/getAllItems")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
    }


    @GetMapping("/getItem")
    public ResponseEntity<ItemResponse> getItemById(@RequestParam String id) {
        ItemResponse itemResponse = itemService.getItemById(id);
        if (itemResponse != null) {
            return ResponseEntity.ok(itemResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateItem")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ItemResponse updateItem(@RequestBody ItemResponse updatedItem) {
        return itemService.updateItem(updatedItem);
    }

    @PutMapping("/updateAllItems")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean updateItems(@RequestBody List<LineItemResponse> lineItemResponse) {
        return itemService.updateItems(lineItemResponse);
    }

    @DeleteMapping("/deleteItem")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteItem(@RequestParam(value = "id") String id) {
        return itemService.deleteItem(id);
    }

}

