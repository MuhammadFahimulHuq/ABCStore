package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.Cart;
import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.Repository.CartRepository;
import com.ecommerce.abcStore.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;

    public Cart saveCart(Cart cart){
        return cartRepository.save(cart);
    }
    public Cart createCart(Integer productID, Integer quanity, User user){
        Product product = productService.getProductById(productID);
        Cart cart = cartRepository.findByUserAndProduct(user,product);
        if(cart != null){
            quanity = cart.getQuantity()+quanity;
            cart.setQuantity(quanity);
        }
        else{
            cart = new Cart();
            cart.setQuantity(quanity);
            cart.setUser(user);
            cart.setProduct(product);
        }

        return saveCart(cart);
    }
    public List<Cart> getCartByUserId(Integer id){
        return cartRepository.findByUserId(id);
    }
    public Cart getCartById(int id){
        return cartRepository.findById(id).orElse(null);
    }
//    public Cart updateCart(String id){
//        List<Cart> updateCart = getCartBySessionId(id);
//        updateCart.
//        return createCart(updateCart);
//    }
//    public String deleteAllCart(String id){
//        Cart findCarts = getCartBySessionId(id);
//        cartRepository.delete(findCarts);
//        return "Cart Deleted";
//    }
    public String deleteCart(int id){
        cartRepository.deleteById(id);
        return "Cart Deleted "+id;
    }
//    public BigDecimal getTotalFromCart(String id){
//        List<Cart> cart =getCartBySessionId(id);
//        return cart.stream()
//                .map(e->e.getProduct().getProductPrice().getPrice())
//                .reduce(BigDecimal::add)
//                .orElse(BigDecimal.ZERO);
//    }
public Integer updateCart(Integer id, Integer quantity){
    Cart cart = getCartById(id);
    cart.setQuantity(quantity);
    saveCart(cart);
    return cart.getQuantity();
}
public float updateQuantity(Long productId, Integer quantity, User user){
        cartRepository.updateQuantity(quantity,productId,user.getId());
    Product product  =    productService.getProductById(productId);
    return getSubTotal(product,quantity);
}

    public float getSubTotal(Product product,Integer quantity) {
        if (product.getProductPrice().getDiscountPrice() == 0.0) {
            return product.getProductPrice().getPrice() * quantity;
        }
        else {
            return product.getProductPrice().getDiscountPrice()*quantity;
        }
    }

public void deleteCartByUserId(List<Cart> carts) {
        cartRepository.deleteAll(carts);
}
}
