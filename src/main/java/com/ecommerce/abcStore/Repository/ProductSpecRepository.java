package com.ecommerce.abcStore.Repository;

import com.ecommerce.abcStore.Model.ProductSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecRepository extends JpaRepository<ProductSpec, Long> {

}
