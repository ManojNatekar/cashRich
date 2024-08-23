package com.example.demo.Config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;

@Service
public class EazyBankUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password;
        List<GrantedAuthority> authorities;
        com.example.demo.Entity.User customer = customerRepository.findByEmail(username);
        if (customer!= null) {
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        } else{
            userName = customer.getEmail();
            password = customer.getPassword();
            authorities = new ArrayList<>();
        }
        return new User(userName,password,authorities);
    }
    
    
    
    

}
