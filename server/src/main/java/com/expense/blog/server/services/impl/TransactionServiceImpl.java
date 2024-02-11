package com.expense.blog.server.services.impl;

import com.expense.blog.server.entities.Category;
import com.expense.blog.server.entities.Transaction;
import com.expense.blog.server.entities.User;
import com.expense.blog.server.exception.ResourcesNotFoundException;
import com.expense.blog.server.payloads.TransactionDto;
import com.expense.blog.server.repositories.CategoryRepo;
import com.expense.blog.server.repositories.TransactionRepo;
import com.expense.blog.server.repositories.UserRepo;
import com.expense.blog.server.services.TransactionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionServices {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public TransactionDto createTransaction(Integer userId, Integer categoryId,TransactionDto transactionDto) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("User","User Id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourcesNotFoundException("Category","Category Id",categoryId));
        Transaction transaction=this.dtoToTransaction(transactionDto);
        transaction.setUser(user);
        transaction.setCategory(category);
        Transaction saved=this.transactionRepo.save(transaction);
        return this.transactionToDto(saved);
    }

    @Override
    public TransactionDto getTransactionById(Integer userId, Integer transactionId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("User","User Id",userId));
        List<Transaction> transactions=this.transactionRepo.findByUser(user);
        Transaction transaction=transactions.stream().filter(obj-> Objects.equals(obj.getTransactionId(),transactionId)).findFirst().orElseThrow(()-> new ResourcesNotFoundException("Transaction","Transaction Id",transactionId));
        return this.transactionToDto(transaction);
    }

    @Override
    public List<TransactionDto> getAllTransactions(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("User","User Id",userId));
        List<Transaction> transactions=this.transactionRepo.findByUser(user);
        return transactions.stream().map(this::transactionToDto).toList();
    }

    @Override
    public List<TransactionDto> getAllTransactionByCategory(Integer userId, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourcesNotFoundException("Category", "Category Id", categoryId));
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourcesNotFoundException("User", "User Id", userId));
        List<Transaction> transactions = this.transactionRepo.findByUserAndCategory(user, category);
        return transactions.stream().map(this::transactionToDto).toList();
    }

    @Override
    public void deleteTransaction(Integer userId, Integer transactionId) {
        TransactionDto transactionDto=this.getTransactionById(userId,transactionId);
        Transaction transaction=this.dtoToTransaction(transactionDto);
        this.transactionRepo.delete(transaction);
    }

    public TransactionDto transactionToDto(Transaction transaction){
        return this.modelMapper.map(transaction,TransactionDto.class);
    }
    public Transaction dtoToTransaction(TransactionDto transactionDto){
        return this.modelMapper.map(transactionDto,Transaction.class);
    }
}
