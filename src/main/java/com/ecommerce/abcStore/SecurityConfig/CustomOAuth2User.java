package com.ecommerce.abcStore.SecurityConfig;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oAuth2User;


    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }
     public String getUserName(){
    return oAuth2User.getAttribute("name");
}
    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }
    public String getFirstName(){ return oAuth2User.getAttribute("firstname");}
    public String getLastName(){return oAuth2User.getAttribute("lastName");}
    public String getPassword(){return oAuth2User.getAttribute("password");}
}