package com.example.demo.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Config.Coin;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CoinRepository;

@Service
public class CoinService {
	private final String COIN_API_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";

	@Value("${coinmarketcap.api.key}")
	private String API_KEY;

	@Autowired
	CoinRepository coinRepository;

	@Autowired
	private UserService userService;

	public String getCoinData(String userName) {

		User user = userService.getUserByUsername(userName);
		if (user != null) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-CMC_PRO_API_KEY", API_KEY);
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<String> response = restTemplate.exchange(COIN_API_URL + "?symbol=BTC,ETH,LTC",
					HttpMethod.GET, entity, String.class);
			if (response.getStatusCodeValue() == 200) {
				try {
					// Parse the JSON and extract the timestamp
					JSONObject jsonObject = new JSONObject(response.getBody());
					JSONObject statusObject = jsonObject.getJSONObject("status");
					String timestamp = statusObject.getString("timestamp");
					Coin coin = new Coin();
					coin.setTimeStamp(timestamp);
					coin.setData(jsonObject.toString());
					coinRepository.save(coin);
					System.out.println(timestamp);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			return response.getBody();

		}
		
		return "";
	}
}
