package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.AuthenticationProvider;
import com.ecommerce.abcStore.Model.Role;
import com.ecommerce.abcStore.Model.User;
import com.ecommerce.abcStore.Repository.RoleRepository;
import com.ecommerce.abcStore.Repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@NoArgsConstructor
public class UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public User findUserById(Integer id){return userRepository.findById(id.longValue()).orElse(null);}

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public Role findByRole(String role){
       Role userRole = roleRepository.findByRole(role);
       return userRole;
    }

    public User saveAdminUser(User user, AuthenticationProvider authProvider){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setAuthenticationProvider(authProvider);
        if (findByRole("ADMIN") == null) {
            Role createRole = new Role();
            createRole.setRole("ADMIN");
            roleRepository.save(createRole);
        }
        user.setRoles(new HashSet<Role>(Arrays.asList(findByRole("ADMIN"))));

        return userRepository.save(user);
    }
    public User savecustomeruser(User user,AuthenticationProvider authProvider){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setAuthenticationProvider(AuthenticationProvider.LOCAL);
        user.setAuthenticationProvider(authProvider);
        if (findByRole("CUSTOMER") == null) {
            Role createRole = new Role();
            createRole.setRole("CUSTOMER");
            roleRepository.save(createRole);
        }
        user.setRoles(new HashSet<Role>(Arrays.asList(findByRole("CUSTOMER"))));


        return userRepository.save(user);
    }
    public User getCurrentlyLoggedInUser(Authentication authentication){
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) principle;
    }



    public void registerNewUserAfterOAuthLoginSuccess(String email, String name, String firstName, String lastName, String password, AuthenticationProvider authProvider) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserName(name);
        user.setActive(true);
        user.setAuthenticationProvider(authProvider);
        userRepository.save(user);

    }

    public void updateExistUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider authProvider) {
    user.setUserName(name);
    user.setAuthenticationProvider(authProvider);
    userRepository.save(user);
    }
}