package com.github.mtlibano.order.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mtlibano.order.domain.City;
import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.domain.Order;
import com.github.mtlibano.order.domain.dto.OrderCityDTO;
import com.github.mtlibano.order.domain.dto.OrderDTO;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping(value = "/reports")
public class ReportResource {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CityService cityService;

	@GetMapping("/order-for-city/{cityId}")
	public ResponseEntity<OrderCityDTO> findOrderForCity(@PathVariable Integer cityId) {
		City city = cityService.findById(cityId);
		List<Client> clients = clientService.findByCity(city);
		List<Order> orders = new ArrayList<>();
		List<OrderDTO> ordersDTO = new ArrayList<>();

		for (Client client : clients) {
			try {
				orders.addAll(orderService.findByClient(client));
			} catch (ObjectNotFound e) {}
		}
		for (Order order : orders) {
			ordersDTO.add(order.toDTO());
		}

		OrderCityDTO orderCityDTO = new OrderCityDTO(city.getDescription(), city.getUf(), ordersDTO);
		return ResponseEntity.ok(orderCityDTO);
	}
	

}
