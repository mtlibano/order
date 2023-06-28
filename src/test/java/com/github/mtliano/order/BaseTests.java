package com.github.mtliano.order;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.github.mtlibano.order.OrderApplication;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.PaymentService;
import com.github.mtlibano.order.services.PhoneNumberService;
import com.github.mtlibano.order.services.ProductService;
import com.github.mtlibano.order.services.RatingService;
import com.github.mtlibano.order.services.impl.CityServiceImpl;
import com.github.mtlibano.order.services.impl.ClientServiceImpl;
import com.github.mtlibano.order.services.impl.PaymentServiceImpl;
import com.github.mtlibano.order.services.impl.PhoneNumberServiceImpl;
import com.github.mtlibano.order.services.impl.ProductServiceImpl;
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
	
	@Bean
	public PaymentService paymentService() {
		return new PaymentServiceImpl();
	}
	
	@Bean
	public ClientService clientService() {
		return new ClientServiceImpl();
	}
	
	@Bean
	public PhoneNumberService phoneNumberService() {
		return new PhoneNumberServiceImpl();
	}
	
	@Bean
	public ProductService productService() {
		return new ProductServiceImpl();
	}

}