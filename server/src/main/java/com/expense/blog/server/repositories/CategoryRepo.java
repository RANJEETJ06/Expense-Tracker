package com.expense.blog.server.repositories;

import com.expense.blog.server.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
