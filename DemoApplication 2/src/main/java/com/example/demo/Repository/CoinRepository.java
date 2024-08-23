package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Config.Coin;


@Repository
public interface CoinRepository extends JpaRepository<Coin, Integer> {
	
	

}
