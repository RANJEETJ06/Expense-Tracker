package com.expense.blog.server.services.impl;

import com.expense.blog.server.entities.Category;
import com.expense.blog.server.entities.Transaction;
import com.expense.blog.server.exception.ResourcesNotFoundException;
import com.expense.blog.server.payloads.CategoryDto;
import com.expense.blog.server.payloads.TransactionDto;
import com.expense.blog.server.repositories.CategoryRepo;
import com.expense.blog.server.repositories.TransactionRepo;
import com.expense.blog.server.services.CategoryServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServicesImpl implements CategoryServices {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TransactionRepo transactionRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.dtoToCategory(categoryDto);
        Category saved=this.categoryRepo.save(category);
        return this.categoryToDto(saved);
    }

    @Override
    public List<TransactionDto> getAllTransactions(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourcesNotFoundException("Category","Category Id",categoryId));
        List<Transaction> transactions=this.transactionRepo.findByCategory(category);
        return transactions.stream().map(this::transactionToDto).toList();
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourcesNotFoundException("Category","Category Id",categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize) {
        Pageable page= PageRequest.of(pageNumber,pageSize);
        Page<Category> categoryPage=this.categoryRepo.findAll(page);
        List<Category> categories=categoryPage.getContent();
        return categories.stream().map(this::categoryToDto).toList();
    }

    @Override
    public CategoryDto getCategoryByTransactionId(Integer transactionId) {
        Transaction transaction=this.transactionRepo.findById(transactionId).orElseThrow(()->new ResourcesNotFoundException("Transaction","Transaction Id:",transactionId));
        Category category=transaction.getCategory();
        return this.categoryToDto(category);
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto,Category.class);
    }
    public CategoryDto categoryToDto(Category category){
        return this.modelMapper.map(category,CategoryDto.class);
    }
    public TransactionDto transactionToDto(Transaction transaction){
        return this.modelMapper.map(transaction,TransactionDto.class);
    }
}
