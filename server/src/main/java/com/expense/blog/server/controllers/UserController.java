package com.expense.blog.server.controllers;

import com.expense.blog.server.payloads.UserDto;
import com.expense.blog.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@EnableMethodSecurity
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto CreatedUserDto=this.userService.createUser(userDto);
        if(CreatedUserDto.getUserId()==null){
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(CreatedUserDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('PUBLIC')")
    @PutMapping("/{UserId}/")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String UserId){
        UserDto UpdatedUserDto=this.userService.userUpdate(userDto, Integer.valueOf(UserId));
        return ResponseEntity.ok(UpdatedUserDto);
    }
    @PreAuthorize("hasAnyAuthority('PUBLIC')")
    @PutMapping("/{UserId}/{Budget}/")
    public ResponseEntity<UserDto> updateBudget(@PathVariable String Budget, @PathVariable String UserId){
        UserDto UpdatedUserDto=this.userService.updateBudget(Integer.valueOf(Budget), Integer.valueOf(UserId));
        return ResponseEntity.ok(UpdatedUserDto);
    }
    @PreAuthorize("hasAuthority('PUBLIC')")
    @DeleteMapping("/{UserId}/")
    public ResponseEntity<?> deleteUser( @PathVariable String UserId){
        this.userService.deleteUser(Integer.valueOf(UserId));
        return new ResponseEntity<>(Map.of("Message","Deleted successfully"),HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('PUBLIC')")
    @GetMapping("/{UserId}/")
    public ResponseEntity<UserDto> GetUser( @PathVariable String UserId){
        UserDto user=this.userService.getUserById(Integer.valueOf(UserId));
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll/")
    public ResponseEntity<List<UserDto>> GetAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "5",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) Integer pageSize){
        List<UserDto> users= this.userService.getAllUsersBySize(pageNumber,pageSize);
        return new ResponseEntity<>(users,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('PUBLIC')")
    @PutMapping("/{UserId}/config3/")
    public ResponseEntity<UserDto> updateUserToPrivate(@PathVariable String UserId){
        UserDto userDto=this.userService.getUserById(Integer.valueOf(UserId));
        UserDto updateToPrivate=this.userService.UpdateToPrivate(userDto);
        return ResponseEntity.ok(updateToPrivate);
    }
    @PreAuthorize("hasAnyAuthority('PRIVATE')")
    @PutMapping("/{UserId}/config1/")
    public ResponseEntity<UserDto> updateUserToAdmin(@PathVariable String UserId){
        UserDto userDto=this.userService.getUserById(Integer.valueOf(UserId));
        UserDto updateToAdmin=this.userService.UpdateToAdmin(userDto);
        return ResponseEntity.ok(updateToAdmin);
    }


}
