package com.ecommerce.abcStore.Controller;


import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.Stock;
import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.SecurityConfig.IAuthenticationFacade;
import com.ecommerce.abcStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.security.Security;

@RestController
@CrossOrigin
public class CartRestController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;



    @PostMapping("/cart/{pid}/{qty}")
    public String addQuantityToCart(@PathVariable("pid") Integer productID, @PathVariable("qty") Integer quantity,Authentication authentication){
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "You must login to add this product to your shopping cart.";
        }
        User user = userService.findUserByEmail(authentication.getName());
        Product product =productService.getProductById(productID);
        if(product == null){
            return "Product is not available.";
        }
        Stock stock =stockService.getStockByProductId(product.getId());
        if(stock == null){
            return product.getProductName() + " out of stock!";
        }
        Integer addedQuantity = cartService.createCart(productID,quantity,user).getQuantity();
        System.out.println("added Item");
        return addedQuantity+" item(s) of this product were added to your shopping cart.";
    }
    @PostMapping("/cart/update/{pid}/{qty}")
    public String updateQuantityToCart(@PathVariable("pid") Long productID, @PathVariable("qty") Integer quantity, Authentication authentication){
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "You must login to add this product to your shopping cart.";
        }
        User user = userService.findUserByEmail(authentication.getName());
        float subtotal = cartService.updateQuantity(productID,quantity,user);

        return String.valueOf(subtotal);
    }

}
