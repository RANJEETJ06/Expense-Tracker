package com.expense.blog.server.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;
    private Integer id;

    public ApiResponse(String message, boolean success,Integer id) {
        this.message=message;
        this.success=success;
        this.id=id;
    }
}
