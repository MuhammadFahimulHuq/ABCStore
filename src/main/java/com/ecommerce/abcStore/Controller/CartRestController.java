package com.ecommerce.abcStore.Controller;


import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.Service.CartService;
import com.ecommerce.abcStore.Service.UserDetailsServiceImp;
import com.ecommerce.abcStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
public class CartRestController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PostMapping("/cart/{pid}/{qty}")
    public String addQuantityToCart(@PathVariable("pid") Integer productID, @PathVariable("qty") Integer quantity, Principal principal){
       System.out.println("addProductTOcart: "+productID+ " - " + quantity);
        if(principal == null ){
            return "You must login to add this product to your shopping cart.";
        }
        User user = userService.findUserByEmail(principal.getName());
        if(user == null){
            return "You are not login!";
        }
        Integer addedQuantity = cartService.createCart(productID,quantity,user).getQuantity();
        System.out.println("added Item");
        return addedQuantity+" item(s) of this product were added to your shopping cart.";
    }
    @PostMapping("/cart/update/{pid}/{qty}")
    public String updateQuantityToCart(@PathVariable("pid") Long productID, @PathVariable("qty") Integer quantity, Principal principal){
        System.out.println("updateProductTOcart: "+productID+ " - " + quantity);
        if(principal == null ){
            return "You must login to add this product to your shopping cart.";
        }
        User user = userService.findUserByEmail(principal.getName());
        if(user == null){
            return "You are not login!";
        }
        float subtotal = cartService.updateQuantity(productID,quantity,user);

        return String.valueOf(subtotal);
    }

}
