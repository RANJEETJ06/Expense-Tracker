package com.expense.blog.server.services;

import com.expense.blog.server.payloads.TransactionDto;

import java.util.List;

public interface TransactionServices {
    TransactionDto createTransaction(Integer userId,Integer categoryId,TransactionDto transactionDto);
    TransactionDto getTransactionById(Integer userId,Integer transactionId);
    List<TransactionDto> getAllTransactions(Integer userId);
    List<TransactionDto> getAllTransactionByCategory(Integer userId,Integer categoryId);
    void deleteTransaction(Integer userId,Integer transactionId);
}
