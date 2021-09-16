package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.Cart;
import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentMethodService paymentMethodService;

@RequestMapping(value = "/cart",method = RequestMethod.GET)
public ModelAndView getCart(Authentication authentication){
    ModelAndView modelAndView = new ModelAndView();
    if(authentication == null){
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
    modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
    modelAndView.addObject("categoryList",categoryService.getAllCategory());
        modelAndView.addObject("cartList",cartService.getCartByUserId(userService.findUserByEmail(authentication.getName()).getId()));
        modelAndView.addObject("cartSize",cartService.getCartByUserId(userService.findUserByEmail(authentication.getName()).getId()).size());
        modelAndView.addObject("userId",userService.findUserByEmail(authentication.getName()).getId());
    modelAndView.addObject("paymentMethodList",paymentMethodService.getAllPaymentMethod());
    modelAndView.setViewName("cart");
    return modelAndView;
}
@RequestMapping(value = "/cart/{id}",method = RequestMethod.GET)
    public ModelAndView createCart(HttpSession session, @PathVariable("id") Integer productId,Principal principle){
    ModelAndView modelAndView = new ModelAndView();
    if(principle == null ){
        modelAndView.addObject("error","You must login to add this product to your shopping cart.");
        return modelAndView;
    }
    User user = userService.findUserByEmail(principle.getName());
    if(user == null){
        modelAndView.addObject("error","You must login to add this product to your shopping cart.");
        return modelAndView;
    }
    cartService.createCart(productId,1,user);
    modelAndView.setViewName("redirect:/cart");
    return modelAndView;
}
@RequestMapping(value = "/cart/delete/{id}",method = RequestMethod.GET)
    public ModelAndView deleteCart(@PathVariable("id") Integer id){
    ModelAndView modelAndView = new ModelAndView();
    Cart cart = cartService.getCartById(id);
    cart.setProduct(null);
    cart.setUser(null);

    cartService.saveCart(cart);
    cartService.deleteCart(id);
    modelAndView.addObject("successDeleteMessage","Cart Successfully Deleted");
    modelAndView.setViewName("redirect:/cart");
    return modelAndView;
}

@RequestMapping(value = "/cart/update/{id}",method = RequestMethod.POST)
    public String updateCart(@PathVariable("id") Integer id,Integer quantity){
    Cart cart = cartService.getCartById(id);

    cart.setQuantity(quantity);
    cartService.saveCart(cart);
    return "Quantity Updated";
}
}
