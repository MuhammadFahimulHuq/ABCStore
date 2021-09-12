package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.SecurityConfig.AuthenticationFacade;
import com.ecommerce.abcStore.SecurityConfig.IAuthenticationFacade;
import com.ecommerce.abcStore.Service.CartService;
import com.ecommerce.abcStore.Service.CategoryService;
import com.ecommerce.abcStore.Service.ProductService;
import com.ecommerce.abcStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collection;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView Home(Authentication authentication,Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products",productService.getAllProduct());
        modelAndView.addObject("categoryList",categoryService.getAllCategory());

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            modelAndView.addObject("cartSize",0);
        }
        else{
            modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
        modelAndView.addObject("cartSize",cartService.getCartByUserId(userService.findUserByEmail(authentication.getName()).getId()).size());
        }

        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value="/category/{id}",method = RequestMethod.GET)
    public ModelAndView getCategoryProduct(@PathVariable("id") Integer id, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        Collection<Product> productList = categoryService.getCategoryById(id).getProducts();
        modelAndView.addObject("categoryList",categoryService.getAllCategory());
        modelAndView.addObject("products",productList);
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            modelAndView.addObject("cartSize",0);
        }
        else{
            modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
            modelAndView.addObject("cartList",cartService.getCartByUserId(userService.findUserByEmail(authentication.getName()).getId()));
            modelAndView.addObject("cartSize",cartService.getCartByUserId(userService.findUserByEmail(authentication.getName()).getId()).size());
        }


        modelAndView.setViewName("home");
        return modelAndView;
    }

}
