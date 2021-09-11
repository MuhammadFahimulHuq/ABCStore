package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.*;
import com.ecommerce.abcStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class PurchaseReceiptController {
    @Autowired
    private PurchaseReceiptService purchaseReceiptService;
    @Autowired
    private CartService cartService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/purchasereceipt/{uid}",method = RequestMethod.GET)
    public ModelAndView getPurchaseReceipt(@PathVariable("uid") Integer userId,Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
        modelAndView.addObject("purchaseReceipt", new PurchaseReceipt());
        modelAndView.addObject("address",new Address());
        modelAndView.addObject("categoryList",categoryService.getAllCategory());
        modelAndView.addObject("cartSize",cartService.getCartByUserId(userId).size());
        modelAndView.addObject("cartList",cartService.getCartByUserId(userId));
        modelAndView.addObject("userId",userService.findUserByEmail(authentication.getName()).getId());
        modelAndView.addObject("paymentMethodList",paymentMethodService.getAllPaymentMethod());
        modelAndView.setViewName("receipt");
        return modelAndView;
    }

    @RequestMapping(value = "/purchasereceipt/{uid}",method = RequestMethod.POST)
    public ModelAndView setPurchaseReceipt(Authentication authentication, @PathVariable("uid") Integer userId, @Valid PurchaseReceipt purchaseReceipt, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        PaymentMethod shipment = purchaseReceipt.getPaymentMethod();
        if (bindingResult.hasErrors() || shipment == null) {
            modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
            modelAndView.addObject("cartList",cartService.getCartByUserId(userId));
            modelAndView.addObject("cartSize",cartService.getCartByUserId(userId).size());
            modelAndView.addObject("userId",userService.findUserByEmail(authentication.getName()).getId());
            modelAndView.addObject("paymentMethodList",paymentMethodService.getAllPaymentMethod());
            modelAndView.setViewName("receipt");
        } else {

            purchaseReceipt.setPaymentMethod(paymentMethodService.getPaymentByMethodPricing(shipment.getMethodPricing()));
            purchaseReceipt.setOrderItems(new ArrayList<>());
            List<Cart> cart =  cartService.getCartByUserId(userId);
            int newq;
            for(Cart i:cart){
                Product product = i.getProduct();
                Stock stock = stockService.getStockByProductId(product.getId());
                newq = stock.getRemainingUnit() - i.getQuantity();
                stock.setRemainingUnit(newq);
                stockService.createStock(stock);
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(i.getQuantity());
                purchaseReceipt.getOrderItems().add(orderItem);
            }

            Date date = new Date();
            purchaseReceipt.setPurchaseDate(date);
            purchaseReceipt.setUser(userService.findUserByEmail(authentication.getName()));
            purchaseReceiptService.createReceipt(purchaseReceipt);
            cartService.deleteCartByUserId(cart);
            modelAndView.setViewName("redirect:/profile");
        }
        return modelAndView;
    }


}
