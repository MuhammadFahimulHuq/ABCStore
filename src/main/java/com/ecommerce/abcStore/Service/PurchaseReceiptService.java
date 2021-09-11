package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.PurchaseReceipt;
import com.ecommerce.abcStore.Repository.PurchaseReceiptRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseReceiptService {

    @Autowired
    private PurchaseReceiptRepository purchaseReceiptRepository;

    public PurchaseReceipt createReceipt(PurchaseReceipt purchaseReceipt){
        return purchaseReceiptRepository.save(purchaseReceipt);
    }
    public List<PurchaseReceipt>getAllPurchaseReceipt(){
        return purchaseReceiptRepository.findAll();
    }
    public List<PurchaseReceipt> getReceiptByUserId(Integer userId){
        return purchaseReceiptRepository.findPurchaseReceiptByUserId(userId);
    }
//    public PurchaseReceipt updateReceipt(Integer userId, PurchaseReceipt purchaseReceipt){
//        List<PurchaseReceipt> findUserReceipt= getReceiptByUserId(userId);
//        findUserReceipt.setAddress(purchaseReceipt.getAddress());
//        findUserReceipt.setOrderItems(purchaseReceipt.getOrderItems());
//        findUserReceipt.setContactNumber(purchaseReceipt.getContactNumber());
//        Date date = new Date();
//        findUserReceipt.setPurchaseDate(date);
//        return createReceipt(findUserReceipt);
//    }


}
