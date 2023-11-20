package com.emart.order.controller;

import com.emart.order.dto.OrderRequest;
import com.emart.order.dto.OrderResponse;
import com.emart.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
       return orderService.placeOrder(orderRequest);
    }


    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllItems();
    }



    @DeleteMapping("/deleteItem")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteOrder(@RequestParam(value = "id") String id) {
        return orderService.deleteOrder(id);
    }

}
