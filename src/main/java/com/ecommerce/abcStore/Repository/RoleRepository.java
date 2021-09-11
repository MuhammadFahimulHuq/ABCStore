package com.ecommerce.abcStore.Repository;

import com.ecommerce.abcStore.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
Role findByRole(String role);
}
