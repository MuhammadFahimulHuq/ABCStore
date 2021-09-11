package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.OrderItem;
import com.ecommerce.abcStore.Repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    public OrderItem createOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);

    }
}
