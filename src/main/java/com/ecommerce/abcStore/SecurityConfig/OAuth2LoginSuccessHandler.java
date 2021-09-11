package com.ecommerce.abcStore.SecurityConfig;

import com.ecommerce.abcStore.Model.AuthenticationProvider;
import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
       String email = oAuth2User.getEmail();
       User user= userService.findUserByEmail(email);
       String name = oAuth2User.getUserName();
       String firstName = oAuth2User.getUserName();
       String lastName= oAuth2User.getUserName();
       String password = oAuth2User.getEmail();
       if(user==null){
           userService.registerNewUserAfterOAuthLoginSuccess(email,name,firstName,lastName,password, AuthenticationProvider.FACEBOOK);
       }
       else {
           userService.updateExistUserAfterOAuthLoginSuccess(user,name, AuthenticationProvider.FACEBOOK);
       }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
