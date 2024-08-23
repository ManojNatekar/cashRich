package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.CoinService;

@RestController
@RequestMapping("/coins")
public class CoinController extends Exception {
    @Autowired
    private CoinService coinService;

    @GetMapping("/{username}")
    public String getCoinData(@PathVariable String username) throws Exception {
  
			if(coinService.getCoinData(username)!="")
				return coinService.getCoinData(username);
			else 
				throw new Exception("user not found!");
    }
}
