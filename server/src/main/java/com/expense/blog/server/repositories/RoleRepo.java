package com.expense.blog.server.repositories;

import com.expense.blog.server.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
