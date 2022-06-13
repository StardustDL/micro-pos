package org.micropos.delivery.service;

import org.micropos.delivery.model.Order;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class OrderCreatedEvent extends RemoteApplicationEvent {
    private Order order;

    public OrderCreatedEvent(){}

    public OrderCreatedEvent(Order data, Object source, String originService, String destinationService) {
        super(source, originService,
                RemoteApplicationEvent.DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        setOrder(data);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}