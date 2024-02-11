package com.expense.blog.server.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
    private Integer transactionId;
    private String description ;
    private Integer amount ;
    Date date;
}
