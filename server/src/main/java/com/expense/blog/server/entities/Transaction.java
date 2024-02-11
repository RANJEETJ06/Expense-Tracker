package com.expense.blog.server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    @Column(name = "description",length = 300)
    private String description ;
    @Column(name = "amount",nullable = false,length = 100)
    private Integer amount ;
    @Column(name = "date",nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date date;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
}
