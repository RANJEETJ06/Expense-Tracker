package com.expense.blog.server.repositories;

import com.expense.blog.server.entities.Category;
import com.expense.blog.server.entities.Transaction;
import com.expense.blog.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
    List<Transaction> findByUser(User user);

    List<Transaction> findByCategory(Category category);

    List<Transaction> findByUserAndCategory(User user, Category category);

}
