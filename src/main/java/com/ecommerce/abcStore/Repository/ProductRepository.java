package com.ecommerce.abcStore.Repository;

import com.ecommerce.abcStore.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product getProductByProductName(String productName);
}
