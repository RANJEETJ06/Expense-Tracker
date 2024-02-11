package com.expense.blog.server.services.impl;

import com.expense.blog.server.configuration.AppConstants;
import com.expense.blog.server.entities.Role;
import com.expense.blog.server.entities.User;
import com.expense.blog.server.exception.ResourcesNotFoundException;
import com.expense.blog.server.payloads.UserDto;
import com.expense.blog.server.repositories.RoleRepo;
import com.expense.blog.server.repositories.UserRepo;
import com.expense.blog.server.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo repoRole;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.userDtoToUser(userDto);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role=this.repoRole.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        if(user.getBudget()==null){
            user.setBudget(0);
        }
        User saved=this.userRepo.save(user);
        return this.userToUserDto(saved);
    }

    @Override
    public UserDto userUpdate(UserDto userDto, Integer userId) {
        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("User","User Id",userId));

        user.setBudget(userDto.getBudget());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User updatedUser=this.userRepo.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto updateBudget(Integer Budget, Integer userId) {
        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("User","User Id",userId));

        user.setBudget(Budget);

        User updatedUser=this.userRepo.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto UpdateToAdmin(UserDto userDto) {
        User user=this.modelMapper.map(userDto,User.class);
        Role role=this.repoRole.findById(AppConstants.ADMIN_USER).get();
        user.getRoles().add(role);
        User saved=this.userRepo.save(user);
        return this.userToUserDto(saved);
    }
    public UserDto UpdateToPrivate(UserDto userDto) {
        User user=this.modelMapper.map(userDto,User.class);
        Role role=this.repoRole.findById(AppConstants.PRIVATE_USER).get();
        user.getRoles().add(role);
        User saved=this.userRepo.save(user);
        return this.userToUserDto(saved);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("User","User Id",userId));
        return this.userToUserDto(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourcesNotFoundException("User","User Id",(long)userId));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto getUserByUserName(String UserName) {
        User user=this.userRepo.findByUserName(UserName).orElseThrow(()-> new ResourcesNotFoundException("User not found",UserName,404));
        return this.userToUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user=this.userRepo.findByEmail(email).orElseThrow(()->new ResourcesNotFoundException("User not found",email,404));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsersBySize(Integer pageNumber, Integer pageSize) {
        Pageable page= PageRequest.of(pageNumber,pageSize);
        Page<User> userPage= this.userRepo.findAll(page);
        List<User> users=userPage.getContent();
        return users.stream().map(this::userToUserDto).toList();
    }

    public User userDtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto,User.class);//this return a user
    }

    public UserDto userToUserDto(User user){
        return this.modelMapper.map(user,UserDto.class);//this return a userDto
    }
}
