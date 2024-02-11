package com.expense.blog.server.security;

import com.expense.blog.server.entities.User;
import com.expense.blog.server.exception.ResourcesNotFoundException;
import com.expense.blog.server.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourcesNotFoundException("User not found",username,404));
        return new UserConfig(user);
    }

}
