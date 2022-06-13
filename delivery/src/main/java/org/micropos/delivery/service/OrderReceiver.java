package org.micropos.delivery.service;

import org.micropos.delivery.model.Delivery;
import org.micropos.delivery.model.Order;
import org.micropos.delivery.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationListener;

@Component
public class OrderReceiver implements ApplicationListener<OrderCreatedEvent> {
    @Autowired
    private DeliveryRepository repository;

    @Override
    public void onApplicationEvent(OrderCreatedEvent event) {
        System.out.println("Order created: " + event.getOrder() + " from " + event.getSource());
        repository.create(new Delivery(event.getOrder().getId(), event.getOrder())).block();
    }
}