package com.ecommerce.abcStore.SecurityConfig;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
