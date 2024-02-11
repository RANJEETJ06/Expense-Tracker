package com.expense.blog.server.services;

import com.expense.blog.server.payloads.CategoryDto;
import com.expense.blog.server.payloads.TransactionDto;

import java.util.List;

public interface CategoryServices {
    CategoryDto createCategory(CategoryDto categoryDto);
    List<TransactionDto> getAllTransactions(Integer categoryId);
    void deleteCategory(Integer categoryId);
    List<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize);
    CategoryDto getCategoryByTransactionId(Integer transactionId);
}
