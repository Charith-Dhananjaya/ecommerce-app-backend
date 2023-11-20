package com.emart.order.service;

import com.emart.order.adapter.OrderAdapter;
import com.emart.order.dto.*;
import com.emart.order.model.Order;
import com.emart.order.model.OrderLineItems;
import com.emart.order.repository.OrderRepository;
import com.emart.order.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient.baseUrl("http://localhost:8081/api").build();
    }


    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToOrderLineItems).toList();

        order.setOrderLineItemsList(orderLineItems);
        List<LineItemResponse> lineItemResponses = orderLineItems.stream()
                .map(orderLineItem -> new LineItemResponse(orderLineItem.getId(), orderLineItem.getQuantity()))
                .toList();


        boolean allProductsInStock= sendLineItemData(lineItemResponses);


        if(allProductsInStock){
            orderRepository.save(order);
            sendTrackingData(order);
            return "Order Placed successfully!";
        } else {
            return "Product is not in stock, please try again later";
        }


    }

    private void sendTrackingData(Order order) {
        OrderTrackingRequest orderTrackingRequest = new OrderTrackingRequest(
                order.getOrderNumber(),
                "Order Placed",
                order.getOrderLineItemsList().get(0).getAddress());

        Boolean block = webClient.post()
                .uri("/location")
                .bodyValue(orderTrackingRequest)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    public boolean sendLineItemData (List < LineItemResponse > lineItemResponses) {
        return Boolean.TRUE.equals(webClient.put()
                .uri("/item/updateAllItems")
                .bodyValue(lineItemResponses)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {

        OrderAdapter orderAdapter = new OrderAdapter(orderLineItemsDto);
        return orderAdapter.mapTomapToOrderLineItems();
    }



    private OrderResponse mapToOrderResponse (Order order){
        return new OrderResponse(
                order.getOrderNumber(),
                order.getOrderLineItemsList()
        );
    }

    public List<OrderResponse> getAllItems(){
        List<Order> items = orderRepository.findAll();
        return items.stream().map(this::mapToOrderResponse).toList();
    }


    public boolean deleteOrder(String id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            orderRepository.delete(order);
            return true;
        }
        return false;
    }
}