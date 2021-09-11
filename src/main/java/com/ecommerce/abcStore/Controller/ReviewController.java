package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.Review;
import com.ecommerce.abcStore.Service.ProductService;
import com.ecommerce.abcStore.Service.ReviewService;
import com.ecommerce.abcStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/product/comment/{id}",method = RequestMethod.POST)
    public ModelAndView setReview(@PathVariable("id") long id, @Valid Review review, BindingResult bindingResult, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("browseProduct");
        }
        review.setProduct(productService.getProductById(id));
        Date date = new Date();
        review.setCreatedAt(date);
        review.setUser(userService.findUserByEmail(authentication.getName()));
        reviewService.createReview(review);
        modelAndView.setViewName("redirect:/view/{id}");
        return  modelAndView;
    }
}
