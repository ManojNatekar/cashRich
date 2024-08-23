package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader("Username") String username,@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getName().equals(username)) {
        
        User existingUser = userService.getUserByUsername(username);
       
        if (existingUser != null) {
        	existingUser.setFirstName(user.getFirstName());
        	existingUser.setLastName(user.getLastName());
        	existingUser.setPassword(user.getPassword()); 
        	existingUser.setMobile(user.getMobile());
        	existingUser.setId( existingUser.getId());
             userService.updateUser(existingUser);
             return ResponseEntity.status(HttpStatus.OK).body("User has been updated");
      
        } 
        
      
        }
        else {
            return ResponseEntity.badRequest().body("User not found");
        }
		return null;
        
    
    }
}