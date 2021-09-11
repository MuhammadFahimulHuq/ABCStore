package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.PurchaseReceipt;
import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.Service.PurchaseReceiptService;
import com.ecommerce.abcStore.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private PurchaseReceiptService purchaseReceiptService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public ModelAndView getProfile(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(authentication.getName());
        List<PurchaseReceipt> userReceipt = purchaseReceiptService.getReceiptByUserId(user.getId());
    modelAndView.addObject("user",user);
    modelAndView.addObject("purchaseReceipt",userReceipt);
    return modelAndView;
    }
}
