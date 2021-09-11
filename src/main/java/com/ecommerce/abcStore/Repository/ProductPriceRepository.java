package com.ecommerce.abcStore.Repository;

import com.ecommerce.abcStore.Model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice,Long> {
}
