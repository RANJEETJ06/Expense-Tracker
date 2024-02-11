package com.expense.blog.server.services;
import com.expense.blog.server.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto userUpdate(UserDto userDto,Integer userId);
    UserDto updateBudget(Integer Budget,Integer userId);
    UserDto UpdateToAdmin(UserDto userDto);
    UserDto UpdateToPrivate(UserDto userDto);
    UserDto getUserById(Integer userId);
    void deleteUser(Integer userId);
    UserDto getUserByUserName(String UserName);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUsersBySize(Integer pageNumber,Integer pageSize);
}
