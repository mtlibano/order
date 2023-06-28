package com.github.mtliano.order;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.github.mtlibano.order.OrderApplication;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.RatingService;
import com.github.mtlibano.order.services.impl.CityServiceImpl;
import com.github.mtlibano.order.services.impl.RatingServiceImpl;

@SpringBootTest(classes = {OrderApplication.class})
@TestConfiguration
@ActiveProfiles("test")
public class BaseTests {
	
	@Bean
	public CityService cityService() {
		return new CityServiceImpl();
	}
	
	@Bean
	public RatingService ratingService() {
		return new RatingServiceImpl();
	}

}