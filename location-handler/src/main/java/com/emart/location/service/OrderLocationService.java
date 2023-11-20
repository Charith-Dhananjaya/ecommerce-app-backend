package com.emart.location.service;


import com.emart.location.model.OrderLocation;
import com.emart.location.dto.OrderLocationRequest;
import com.emart.location.dto.OrderLocationResponse;
import com.emart.location.repository.OrderLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderLocationService {
    private final OrderLocationRepository orderLocationRepository;

    @Autowired
    public OrderLocationService(OrderLocationRepository orderLocationRepository) {
        this.orderLocationRepository = orderLocationRepository;
    }

    public void createOrderLocation(OrderLocationRequest orderLocationRequest) {
        UUID id = UUID.randomUUID();
        OrderLocation orderLocation = new OrderLocation(
                id.toString(),
                orderLocationRequest.getOrderId(),
                orderLocationRequest.getStatus(),
                orderLocationRequest.getLocation()
        );
        orderLocationRepository.save(orderLocation);
    }

    public OrderLocationResponse mapToorderTrackingResponse(OrderLocation orderLocation){
        return new OrderLocationResponse(
                orderLocation.getId(),
                orderLocation.getOrderId(),
                orderLocation.getStatus(),
                orderLocation.getLocation()
        );
    }

    public List<OrderLocationResponse> getOrderTrackingByOrderId(String orderId) {
        List<OrderLocation> orderLocation = orderLocationRepository.findByOrderId(orderId);
         return orderLocation.stream().map(this::mapToorderTrackingResponse).collect(Collectors.toList());
    }

    public OrderLocationResponse updateStatus(String orderId, String status) {
        List<OrderLocation> orderLocation = orderLocationRepository.findByOrderId(orderId);
        OrderLocation orderLocation1 = new OrderLocation(
        UUID.randomUUID().toString(),
        orderId,
        status,
        orderLocation.get(0).getLocation());
        orderLocationRepository.save(orderLocation1);
        return mapToorderTrackingResponse(orderLocation1);

    }

}

