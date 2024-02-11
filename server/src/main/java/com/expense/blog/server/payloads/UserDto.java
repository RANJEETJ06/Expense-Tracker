package com.expense.blog.server.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer userId;
    private String userName;
    private String email;
    private String password;
    private Integer Budget;
}
