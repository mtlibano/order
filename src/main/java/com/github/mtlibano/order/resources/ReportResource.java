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
import com.github.mtlibano.order.domain.Payment;
import com.github.mtlibano.order.domain.Product;
import com.github.mtlibano.order.domain.ProductOrder;
import com.github.mtlibano.order.domain.dto.ClientDTO;
import com.github.mtlibano.order.domain.dto.ClientPaymentDTO;
import com.github.mtlibano.order.domain.dto.OrderByClientPaymentDTO;
import com.github.mtlibano.order.domain.dto.OrderCityDTO;
import com.github.mtlibano.order.domain.dto.OrderDTO;
import com.github.mtlibano.order.domain.dto.PaymentClientDTO;
import com.github.mtlibano.order.domain.dto.PaymentDTO;
import com.github.mtlibano.order.domain.dto.ProductByClientOrderDTO;
import com.github.mtlibano.order.domain.dto.ProductClientDTO;
import com.github.mtlibano.order.domain.dto.ProductDTO;
import com.github.mtlibano.order.domain.dto.RatingClientDTO;
import com.github.mtlibano.order.domain.dto.RatingDTO;
import com.github.mtlibano.order.domain.dto.RatingProductDTO;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.PaymentService;
import com.github.mtlibano.order.services.ProductOrderService;
import com.github.mtlibano.order.services.ProductService;
import com.github.mtlibano.order.services.RatingService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;
import com.github.mtlibano.order.utils.DateUtils;

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
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/order-by-client-payment/{clientId}/{paymentId}")
	public ResponseEntity<OrderByClientPaymentDTO> findOrderByClientAndPayment(@PathVariable Integer clientId, @PathVariable Integer paymentId) {
		Client client = clientService.findById(clientId);
		Payment payment = paymentService.findById(paymentId);
		List<Order> orders = orderService.findByClient(client);
		List<OrderDTO> orderDTO = new ArrayList<>();
		
		for (Order order : orders) {
			if (order.getPayment().getId() == paymentId) {
				orderDTO.add(order.toDTO());	
			}
		}

		OrderByClientPaymentDTO orderByClientPaymentDTO = new OrderByClientPaymentDTO(client.getName(), client.getCpf(), payment.getType(), orderDTO);
		return ResponseEntity.ok(orderByClientPaymentDTO);
	}
	
	@GetMapping("/product-by-client-order/{clientId}/{orderId}")
	public ResponseEntity<ProductByClientOrderDTO> findProductByClientAndOrder(@PathVariable Integer clientId, @PathVariable Integer orderId) {
		Order order = orderService.findById(orderId);
		Client client = clientService.findById(clientId);
		List<ProductOrder> productOrders = productOrderService.findByOrder(order);	
		List<ProductDTO> productDTO = new ArrayList<>();
		
		for (ProductOrder productOrder : productOrders) {
			if (productOrder.getOrder().getClient().getId() == clientId) {
				productDTO.add(productOrder.getProduct().toDTO());	
			}
		}

		ProductByClientOrderDTO productByClientOrderDTO = new ProductByClientOrderDTO(
				client.getName(), 
				client.getCpf(), 
				order.getId(), 
				DateUtils.zonedDateTimeToStr(order.getDate()),
				productDTO);
		return ResponseEntity.ok(productByClientOrderDTO);
	}

	@GetMapping("/order-by-city/{cityId}")
	public ResponseEntity<OrderCityDTO> findOrderByCity(@PathVariable Integer cityId) {
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
	
	@GetMapping("/rating-by-product/{productId}")
	public ResponseEntity<RatingProductDTO> findRatingByProduct(@PathVariable Integer productId) {
		Product product = productService.findById(productId);
		List<ProductOrder> productOrders = productOrderService.findByProduct(product);
		List<Order> orders = new ArrayList<>();
		List<RatingDTO> ratingsDTO = new ArrayList<>();

		for (ProductOrder productOrder : productOrders) {
			try {
				orders.add(orderService.findById(productOrder.getOrder().getId()));
			} catch (ObjectNotFound e) {}
		}
		for (Order order : orders) {
			ratingsDTO.add(ratingService.findById(order.getRating().getId()).toDTO());
		}
		
		RatingProductDTO ratingProductDTO = new RatingProductDTO(product.getDescription(), product.getPrice(), ratingsDTO);
		return ResponseEntity.ok(ratingProductDTO);
	}

	@GetMapping("/rating-by-client/{clientId}")
	public ResponseEntity<RatingClientDTO> findRatingByClient(@PathVariable Integer clientId) {
		Client client = clientService.findById(clientId);
		List<Order> orders = orderService.findByClient(client);
		List<RatingDTO> ratingsDTO = new ArrayList<>();
		
		for (Order order : orders) {
			ratingsDTO.add(ratingService.findById(order.getRating().getId()).toDTO());
		}

		RatingClientDTO ratingClientDTO = new RatingClientDTO(client.getName(), client.getCpf(), ratingsDTO);
		return ResponseEntity.ok(ratingClientDTO);
	}
	
	@GetMapping("/product-by-client/{clientId}")
	public ResponseEntity<ProductClientDTO> findProductByClient(@PathVariable Integer clientId) {
		Client client = clientService.findById(clientId);
		List<Order> orders = orderService.findByClient(client);
		List<ProductOrder> productOrders = new ArrayList<>();
		List<ProductDTO> productDTO = new ArrayList<>();
		
		for (Order order : orders) {
			try {
				productOrders.addAll(productOrderService.findByOrder(order));
			} catch (ObjectNotFound e) {}
		}
		for (ProductOrder productOrder : productOrders) {
			productDTO.add(productService.findById(productOrder.getProduct().getId()).toDTO());
		}

		ProductClientDTO productClientDTO = new ProductClientDTO(client.getName(), client.getCpf(), productDTO);
		return ResponseEntity.ok(productClientDTO);
	}
	
	@GetMapping("/payment-by-client/{clientId}")
	public ResponseEntity<PaymentClientDTO> findPaymentByClient(@PathVariable Integer clientId) {
		Client client = clientService.findById(clientId);
		List<Order> orders = orderService.findByClient(client);
		List<PaymentDTO> paymentDTO = new ArrayList<>();
		
		for (Order order : orders) {
			paymentDTO.add(paymentService.findById(order.getPayment().getId()).toDTO());
		}

		PaymentClientDTO paymentClientDTO = new PaymentClientDTO(client.getName(), client.getCpf(), paymentDTO);
		return ResponseEntity.ok(paymentClientDTO);
	}
	
	@GetMapping("/client-by-payment/{paymentId}")
	public ResponseEntity<ClientPaymentDTO> findClientByPayment(@PathVariable Integer paymentId) {
		Payment payment = paymentService.findById(paymentId);
		List<Order> orders = orderService.findByPayment(payment);
		List<ClientDTO> clientDTO = new ArrayList<>();
		
		for (Order order : orders) {
			clientDTO.add(clientService.findById(order.getClient().getId()).toDTO());
		}

		ClientPaymentDTO clientPaymentDTO = new ClientPaymentDTO(payment.getId(), payment.getType(), clientDTO);
		return ResponseEntity.ok(clientPaymentDTO);
	}
	
}