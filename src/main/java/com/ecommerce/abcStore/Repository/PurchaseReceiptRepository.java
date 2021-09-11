package com.ecommerce.abcStore.Repository;

import com.ecommerce.abcStore.Model.PurchaseReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseReceiptRepository extends JpaRepository<PurchaseReceipt,Integer> {
   List<PurchaseReceipt> findPurchaseReceiptByUserId(Integer userId);
}
