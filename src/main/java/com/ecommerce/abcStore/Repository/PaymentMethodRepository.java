package com.ecommerce.abcStore.Repository;

import com.ecommerce.abcStore.Model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {
    PaymentMethod findByMethodType(String methodType);

    PaymentMethod findByMethodPricing(float methodPricing);
}
