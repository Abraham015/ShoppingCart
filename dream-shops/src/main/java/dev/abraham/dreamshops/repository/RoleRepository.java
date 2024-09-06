package dev.abraham.dreamshops.repository;

import com.fasterxml.jackson.databind.JsonNode;
import dev.abraham.dreamshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(String role);
}
