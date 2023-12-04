package com.nth.mike.repository;

import com.nth.mike.entity.Role;
import com.nth.mike.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByName(RoleName name);
}
