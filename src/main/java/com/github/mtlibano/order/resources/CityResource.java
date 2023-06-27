package com.github.mtlibano.order.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mtlibano.order.services.CityService;

@RestController
@RequestMapping(value = "/city")
public class CityResource {
	
	@Autowired
	CityService service;
	
	

}