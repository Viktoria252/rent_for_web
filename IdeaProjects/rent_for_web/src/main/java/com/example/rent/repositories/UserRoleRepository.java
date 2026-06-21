package com.example.rent.repositories;

import com.example.rent.models.entities.Role;
import com.example.rent.models.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByName(UserRoles role);
}
