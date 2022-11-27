package com.dochien0204.codeproject.repositories;

import com.dochien0204.codeproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  List<Role> findByRoleName(String roleName);
}
