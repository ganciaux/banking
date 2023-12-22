package com.ganc.banking.repositories;

import com.ganc.banking.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(String roleName);
}
