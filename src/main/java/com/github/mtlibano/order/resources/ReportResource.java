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
import com.github.mtlibano.order.domain.ProductOrder;
import com.github.mtlibano.order.domain.Rating;
import com.github.mtlibano.order.domain.dto.OrderByCityDTO;
import com.github.mtlibano.order.domain.dto.OrderDTO;
import com.github.mtlibano.order.domain.dto.ProductByClientDTO;
import com.github.mtlibano.order.domain.dto.ProductDTO;
import com.github.mtlibano.order.domain.dto.RatingByClientDTO;
import com.github.mtlibano.order.domain.dto.RatingDTO;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.ProductOrderService;
import com.github.mtlibano.order.services.RatingService;
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
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ProductOrderService productOrderService;

	@GetMapping("/order-by-city/{cityId}")
	public ResponseEntity<OrderByCityDTO> findOrderByCity(@PathVariable Integer cityId) {
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

		OrderByCityDTO orderCityDTO = new OrderByCityDTO(city.getDescription(), city.getUf(), ordersDTO);
		return ResponseEntity.ok(orderCityDTO);
	}
	
	@GetMapping("/rating-by-client/{clientId}")
	public ResponseEntity<RatingByClientDTO> findRatingByClient(@PathVariable Integer clientId) {
		Client client = clientService.findById(clientId);
		List<Order> orders = orderService.findByClient(client);
		List<Rating> ratings = ratingService.listAll();
		List<RatingDTO> ratingsDTO = new ArrayList<>();
		
		for (Rating rating : ratings) {
			for (int i = 0; i < orders.size(); i++) {
				try {
					if (rating.getId() == orders.get(i).getRating().getId()) {
						ratingsDTO.add(rating.toDTO());
					}
				} catch (ObjectNotFound e) {}
			}
		}

		RatingByClientDTO ratingByClientDTO = new RatingByClientDTO(client.getName(), client.getCpf(), ratingsDTO);
		return ResponseEntity.ok(ratingByClientDTO);
	}
	
	@GetMapping("/product-by-client/{clientId}")
	public ResponseEntity<ProductByClientDTO> findProductByClient(@PathVariable Integer clientId) {
		Client client = clientService.findById(clientId);
		List<Order> orders = orderService.findByClient(client);
		List<ProductOrder> productOrders = productOrderService.listAll();
		List<ProductDTO> productDTO = new ArrayList<>();
		
		for (Order order : orders) {
			for (int i = 0; i < productOrders.size(); i++) {
				try {
					if (order.getId() == productOrders.get(i).getOrder().getId()) {
						productDTO.add(productOrders.get(i).getProduct().toDTO());
					}
				} catch (ObjectNotFound e) {}
			}
		}

		ProductByClientDTO productByClientDTO = new ProductByClientDTO(client.getName(), client.getCpf(), productDTO);
		return ResponseEntity.ok(productByClientDTO);
		
		/*
		var client = clientService.findById(clientId);
		var orders = orderService.findByClient(client);
		var productOrders = productOrderService.listAll();

		var productDTO = productOrders.stream()
		    .filter(po -> orders.stream()
		        .map(Order::getId)
		        .anyMatch(orderId -> orderId == po.getOrder().getId())
		    )
		    .map(po -> po.getProduct().toDTO())
		    .toList();

		var productByClientDTO = new ProductByClientDTO(client.getName(), client.getCpf(), productDTO);
		return ResponseEntity.ok(productByClientDTO);
		*/

	}
	
}