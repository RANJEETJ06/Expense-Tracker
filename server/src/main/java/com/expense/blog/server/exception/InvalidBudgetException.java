package com.expense.blog.server.exception;

public class InvalidBudgetException extends RuntimeException{
        long fieldValue;

        public InvalidBudgetException(long fieldValue) {
            super(String.format("Budget is Not correct with:%s increase it",fieldValue));
            this.fieldValue = fieldValue;
        }
}
