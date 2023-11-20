package com.emart.location.repository;

import com.emart.location.model.OrderLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderLocationRepository extends MongoRepository<OrderLocation, String> {

    List<OrderLocation> findByOrderId(String orderId);
}
