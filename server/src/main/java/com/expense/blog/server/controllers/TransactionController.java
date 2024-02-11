package com.expense.blog.server.controllers;

import com.expense.blog.server.payloads.TransactionDto;
import com.expense.blog.server.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
@EnableMethodSecurity
public class TransactionController {
    @Autowired
    private TransactionServices transactionServices;

    @PostMapping("/{userId}/{categoryId}/")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto, @PathVariable String userId,@PathVariable String categoryId){
        TransactionDto createdTransaction=this.transactionServices.createTransaction(Integer.valueOf(userId),Integer.valueOf(categoryId),transactionDto);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/{transactionId}/")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<?> deleteTransaction(@PathVariable String userId, @PathVariable String transactionId){
        this.transactionServices.deleteTransaction(Integer.valueOf(userId), Integer.valueOf(transactionId));
        return new ResponseEntity<>(Map.of("Message","Deleted successfully"),HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<List<TransactionDto>> getAllTransaction(@PathVariable String userId){
        List<TransactionDto> transactionDtoList=this.transactionServices.getAllTransactions(Integer.valueOf(userId));
        return new ResponseEntity<>(transactionDtoList,HttpStatus.CREATED);
    }
    @GetMapping("/{userId}/{categoryId}/")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<List<TransactionDto>> getAllTransactionByCategory(@PathVariable String userId,@PathVariable String categoryId){
        List<TransactionDto> transactionDtoList=this.transactionServices.getAllTransactionByCategory(Integer.valueOf(userId), Integer.valueOf(categoryId));
        return new ResponseEntity<>(transactionDtoList,HttpStatus.CREATED);
    }
    @GetMapping("/get/{userId}/{transactionId}/")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<TransactionDto> getTransactionBYTransactionId(@PathVariable String userId,@PathVariable String transactionId){
        TransactionDto transaction=this.transactionServices.getTransactionById(Integer.valueOf(userId), Integer.valueOf(transactionId));
        return new ResponseEntity<>(transaction,HttpStatus.CREATED);
    }
}
