package com.example.demo.Config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.Repository.UserRepository;


@Component
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {
	
	
	
	@Autowired
    private UserRepository customerRepository;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username=authentication.getName();
		String password=authentication.getCredentials().toString();
		 com.example.demo.Entity.User customer = customerRepository.findByUsername(username);
		if(customer!= null)
		{
			if(passwordEncoder.matches(password, customer.getPassword()))
			{ 
				List<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority("ROLE_PLACEHOLDER"));
				return new UsernamePasswordAuthenticationToken(username,password,authorities);
				
			}
			else {
				
				throw new BadCredentialsException("Invalid Password");
			}
			
		}
		else {
			
			throw new BadCredentialsException("No user register with this details...");
		}
		
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
