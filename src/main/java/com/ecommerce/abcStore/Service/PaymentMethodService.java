package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.Category;
import com.ecommerce.abcStore.Model.PaymentMethod;
import com.ecommerce.abcStore.Repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;


    public PaymentMethod createPayment(PaymentMethod paymentMethod){
     return   paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> getAllPaymentMethod(){
        return paymentMethodRepository.findAll();
    }
    public PaymentMethod getPaymentMethodByID(int id){
        return paymentMethodRepository.findById(id).orElse(null);
    }
    public PaymentMethod updatePaymentMethod(int id, PaymentMethod paymentMethod){
        PaymentMethod findPaymentMethod = getPaymentMethodByID(id);
        findPaymentMethod.setMethodType(paymentMethod.getMethodType());
        findPaymentMethod.setMethodPricing(paymentMethod.getMethodPricing());
        return createPayment(paymentMethod);
    }
    public String deletePaymentMethod(int id){
        PaymentMethod findPaymentMethod = getPaymentMethodByID(id);
        paymentMethodRepository.delete(findPaymentMethod);
        return "paymentMethod id : "+findPaymentMethod.getId()+ "deleted";
    }
    public PaymentMethod getPaymentMethodByMethodId(String methodType){
        return paymentMethodRepository.findByMethodType(methodType);
    }
    public PaymentMethod getPaymentByMethodPricing(float price){
        return paymentMethodRepository.findByMethodPricing(price);
    }
}
