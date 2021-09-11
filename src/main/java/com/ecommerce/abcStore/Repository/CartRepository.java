package com.ecommerce.abcStore.Repository;

import com.ecommerce.abcStore.Model.Cart;
import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(Integer id);

    Cart findByUserAndProduct(User user, Product product);
    @Query("UPDATE Cart c set c.quantity = ?1 WHERE c.product.id= ?2 "
    + "AND c.user.id = ?3")
    @Modifying
    void updateQuantity(Integer quantity, Long productId, Integer userId);

    void deleteByUserId(int id);
}
