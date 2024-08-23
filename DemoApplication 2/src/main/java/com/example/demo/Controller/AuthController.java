package com.example.demo.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Config.EazyBankUsernamePwdAuthenticationProvider;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    

    @Autowired
    private EazyBankUsernamePwdAuthenticationProvider authenticationProvider;
    

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@Valid @RequestBody User user,BindingResult bindingResult) {
    	try {
    	if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    	User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    
    }
    catch(Exception unique)
    {
    	 return new ResponseEntity<>(unique.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestHeader("Authorization") String authHeader) {
        Map<String, String> response = new HashMap<>();
        try {
      
            String[] credentials = extractCredentials(authHeader);
            String username = credentials[0];
            String password = credentials[1];

         
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationProvider.authenticate(authToken);

            response.put("message", "Login successful");
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            response.put("error", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        } catch (AuthenticationException e) {
            response.put("error", "Authentication failed");
            return ResponseEntity.status(401).body(response);
        }
    }

    private String[] extractCredentials(String authHeader) {
        // Remove "Basic " prefix and decode the credentials
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials));
        // credentials are in "username:password" format
        return credentials.split(":", 2);
    }
    
    
}
