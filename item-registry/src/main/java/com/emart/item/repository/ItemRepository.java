package com.emart.item.repository;

import com.emart.item.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository <Item,String> {
    List<Item> findByIdIn(List<String> ids);
}
