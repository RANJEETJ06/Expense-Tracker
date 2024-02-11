package com.expense.blog.server.controllers;

import com.expense.blog.server.payloads.CategoryDto;
import com.expense.blog.server.payloads.TransactionDto;
import com.expense.blog.server.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@EnableMethodSecurity
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto createdCategoryDto=this.categoryServices.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteCategory( @PathVariable String categoryId){
        this.categoryServices.deleteCategory(Integer.valueOf(categoryId));
        return new ResponseEntity<>(Map.of("Message","Deleted successfully"),HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<TransactionDto>> GetCategory(@PathVariable String categoryId){
        List<TransactionDto> transactionDtoList= this.categoryServices.getAllTransactions(Integer.valueOf(categoryId));
        return new ResponseEntity<>(transactionDtoList,HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<List<CategoryDto>> GetAllCategories(
            @RequestParam(value = "pageNumber",defaultValue = "6",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) Integer pageSize){
        List<CategoryDto> categoryDtoList= this.categoryServices.getAllCategory(pageNumber, pageSize);
        return new ResponseEntity<>(categoryDtoList,HttpStatus.CREATED);
    }
    @GetMapping("/get/{transactionId}/")
    @PreAuthorize("hasAuthority('PUBLIC')")
    public ResponseEntity<CategoryDto> getCategoryByTransactionId(@PathVariable String transactionId){
        CategoryDto categoryDto=this.categoryServices.getCategoryByTransactionId(Integer.valueOf(transactionId));
        return new ResponseEntity<>(categoryDto,HttpStatus.CREATED);
    }
}
