package com.emart.location.controller;

import com.emart.location.dto.OrderLocationRequest;
import com.emart.location.dto.OrderLocationResponse;
import com.emart.location.service.OrderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class OrderLocationController {
    private final OrderLocationService orderLocationService;

    @Autowired
    public OrderLocationController(OrderLocationService orderLocationService) {
        this.orderLocationService = orderLocationService;
    }

    @PostMapping
    public void createOrderLocation(@RequestBody OrderLocationRequest orderTracking) {
        orderLocationService.createOrderLocation(orderTracking);
    }

    @GetMapping("/{orderId}")
    public List<OrderLocationResponse> getOrderLocationByOrderId(@PathVariable String orderId) {
        return orderLocationService.getOrderTrackingByOrderId(orderId);
    }

    @PostMapping("/update")
    public OrderLocationResponse updateState(@RequestBody OrderLocationRequest updateState) {
        return orderLocationService.updateStatus(updateState.getOrderId(), updateState.getStatus());
    }

}
