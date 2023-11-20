package com.emart.order.adapter;

import com.emart.order.model.OrderLineItems;
import com.emart.order.dto.OrderLineItemsDto;

public class OrderAdapter {
    private final OrderLineItemsDto orderLineItemsDto;

    public OrderAdapter(OrderLineItemsDto orderLineItemsDto) {
        this.orderLineItemsDto = orderLineItemsDto;
    }

    public OrderLineItems mapTomapToOrderLineItems() {

        OrderLineItems orderLineItems = new OrderLineItems(
                orderLineItemsDto.getId(),
                orderLineItemsDto.getName(),
                orderLineItemsDto.getAddress(),
                orderLineItemsDto.getDescription(),
                orderLineItemsDto.getQuantity(),
                orderLineItemsDto.getPrice()
        );
        return orderLineItems;
    }
}
