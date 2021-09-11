package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.AuthenticationProvider;
import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.Service.CategoryService;
import com.ecommerce.abcStore.Service.UserDetailsServiceImp;
import com.ecommerce.abcStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuthController {


    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @RequestMapping(value = {"/login"},method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView customerRegistration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createCustomerRegistration(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userUsernameExists = userService.findUserByUserName(user.getUserName());
        User userEmailExists = userService.findUserByEmail(user.getEmail());
        if (userUsernameExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        else if(userEmailExists != null){
            bindingResult
                    .rejectValue("email","error.user",
                            "There is already a user registered with the email provided");
        }
        else if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.savecustomeruser(user, AuthenticationProvider.LOCAL);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/login");

        }
        return modelAndView;
    }

    @RequestMapping(value="/registration/admin", method = RequestMethod.GET)
    public ModelAndView adminRegistration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration_admin");
        return modelAndView;
    }
    @RequestMapping(value = "/registration/admin", method = RequestMethod.POST)
    public ModelAndView createAdminRegistration(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userUsernameExists = userService.findUserByUserName(user.getUserName());
        User userEmailExists = userService.findUserByEmail(user.getEmail());
        if (userUsernameExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        else if(userEmailExists != null){
            bindingResult
                    .rejectValue("email","error.user",
                            "There is already a user registered with the email provided");
        }
        else if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration_admin");
        } else {
            userService.saveAdminUser(user,AuthenticationProvider.FACEBOOK);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/login");

        }
        return modelAndView;}
}
