package com.ecommerce.abcStore.Controller;


import com.ecommerce.abcStore.Model.PaymentMethod;
import com.ecommerce.abcStore.Service.PaymentMethodService;
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

@Controller
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/paymentmethod",method = RequestMethod.GET)
    public ModelAndView getPayment(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("paymentMethod",new PaymentMethod());
        modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
        modelAndView.addObject("paymentMethodList",paymentMethodService.getAllPaymentMethod());
        modelAndView.setViewName("paymentMethod");
        return modelAndView;
    }
    @RequestMapping(value = "/paymentmethod",method = RequestMethod.POST)
    public ModelAndView setPayment(@Valid PaymentMethod paymentMethod, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        PaymentMethod existPaymentMethod = paymentMethodService.getPaymentMethodByMethodId(paymentMethod.getMethodType());
        if(existPaymentMethod != null){
        bindingResult
                .rejectValue("methodType","error.paymentMethod",
                        "Already PaymentMethod Exist");
        } else if (bindingResult.hasErrors()) {
            modelAndView.setViewName("paymentMethod");
        }else{
            paymentMethodService.createPayment(paymentMethod);
            modelAndView.addObject("successMessage","New Payment Method Created");
            modelAndView.addObject("paymentMethod",new PaymentMethod());
            modelAndView.setViewName("redirect:/paymentmethod");
        }
        return modelAndView;
        }
    @RequestMapping(value ="/paymentmethod/update/{id}",method = RequestMethod.GET)
    public ModelAndView updatePaymentMethod(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodByID(id);
        modelAndView.addObject("paymentMethod",paymentMethod);
        modelAndView.setViewName("paymentMethod_update");
        return modelAndView;

    }


    @RequestMapping(value = "/paymentmethod/update/{id}",method = RequestMethod.POST)
    public ModelAndView updatePaymentMethod(@PathVariable("id")Integer id,@Valid PaymentMethod paymentMethod){
        ModelAndView modelAndView = new ModelAndView();
        paymentMethodService.updatePaymentMethod(id,paymentMethod);
        modelAndView.setViewName("redirect:/paymentmethod");
        return modelAndView;
    }
    @RequestMapping(value ="/paymentmethod/delete/{id}")
    public ModelAndView deletePaymentMethod(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        paymentMethodService.deletePaymentMethod(id);
        modelAndView.addObject("successDeleteMessage","Payment Method Successfully Deleted");
        modelAndView.setViewName("redirect:/paymentmethod");
        return modelAndView;
    }

}
