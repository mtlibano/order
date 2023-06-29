package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.Order;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.PaymentService;
import com.github.mtlibano.order.services.RatingService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class OrderServiceTest extends BaseTests {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	RatingService ratingService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var order = orderService.findById(1);
		assertThat(order).isNotNull();
		assertEquals(1, order.getId());
		assertEquals("Max", order.getClient().getName());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> orderService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAllTest() {
		var list = orderService.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> orderService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void insertTest() {
		var order = orderService.insert(new Order(null, ZonedDateTime.of(2023, 06, 06, 0, 0, 0, 0, ZoneId.systemDefault()), clientService.findById(1), paymentService.findById(1), ratingService.findById(1)));
		assertThat(order).isNotNull();
		var newOrder = orderService.findById(1);
		assertEquals(1, newOrder.getId());
		assertEquals("Pix", newOrder.getPayment().getType());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Data")
	@Sql({"classpath:/resources/sqls/order.sql"})
	void insertErrorDateTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> orderService.insert(new Order(null, null, clientService.findById(1), paymentService.findById(1), ratingService.findById(1))));
		assertEquals("Data inválida!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Cliente")
	@Sql({"classpath:/resources/sqls/order.sql"})
	void insertErrorClientTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> orderService.insert(new Order(null, ZonedDateTime.of(2023, 06, 06, 0, 0, 0, 0, ZoneId.systemDefault()), null, paymentService.findById(1), ratingService.findById(1))));
		assertEquals("Cliente inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Pgamento")
	@Sql({"classpath:/resources/sqls/order.sql"})
	void insertErrorPaymentTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> orderService.insert(new Order(null, ZonedDateTime.of(2023, 06, 06, 0, 0, 0, 0, ZoneId.systemDefault()), clientService.findById(1), null, ratingService.findById(1))));
		assertEquals("Pagamento inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void updateTest() {
		orderService.update(new Order(5, ZonedDateTime.of(2023, 06, 06, 0, 0, 0, 0, ZoneId.systemDefault()), clientService.findById(1), paymentService.findById(5), ratingService.findById(1)));
		var order = orderService.findById(5);
		assertEquals("Cartão Crédito", order.getPayment().getType());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/order.sql"})
	void deleteTest() {
		orderService.delete(2);
		var list = orderService.listAll();
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Data")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDate() {
		var list = orderService.findByDate(ZonedDateTime.of(2023, 06, 02, 0, 0, 0, 0, ZoneId.systemDefault()));
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Data ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDateError() {
		var exception = assertThrows(ObjectNotFound.class, () -> orderService.findByDate(ZonedDateTime.of(2023, 06, 10, 0, 0, 0, 0, ZoneId.systemDefault())));
		assertEquals("Nenhum pedido com essa data: 2023-06-10T00:00-03:00[America/Sao_Paulo]", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por intervalo de Data")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDateBetween() {
		var list = orderService.findByDateBetween(ZonedDateTime.of(2023, 06, 01, 0, 0, 0, 0, ZoneId.systemDefault()), ZonedDateTime.of(2023, 06, 02, 0, 0, 0, 0, ZoneId.systemDefault()));
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Buscar por intervalo de Data ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDateBetweenError() {
		var exception = assertThrows(ObjectNotFound.class, () -> orderService.findByDateBetween(ZonedDateTime.of(2023, 06, 10, 0, 0, 0, 0, ZoneId.systemDefault()), ZonedDateTime.of(2023, 06, 12, 0, 0, 0, 0, ZoneId.systemDefault())));
		assertEquals("Nenhum pedido nesse intervalo de data: 2023-06-10T00:00-03:00[America/Sao_Paulo] e 2023-06-12T00:00-03:00[America/Sao_Paulo]", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por Cliente")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByClient() {
		var list = orderService.findByClient(clientService.findById(1));
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Cliente ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByClientError() {
		var exception = assertThrows(ObjectNotFound.class, () -> orderService.findByClient(clientService.findById(3)));
		assertEquals("Nenhum pedido para esse cliente: Mari!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por Pagamento")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPayment() {
		var list = orderService.findByPayment(paymentService.findById(2));
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Pagamento ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPaymentError() {
		var exception = assertThrows(ObjectNotFound.class, () -> orderService.findByPayment(paymentService.findById(5)));
		assertEquals("Nenhum pedido para esse tipo de pagamento: Cartão Crédito!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por Avaliação")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByRating() {
		var list = orderService.findByRating(ratingService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Avaliação ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByRatingError() {
		var exception = assertThrows(ObjectNotFound.class, () -> orderService.findByRating(ratingService.findById(6)));
		assertEquals("Nenhum pedido com essa avaliação: 6!", exception.getMessage());
	}

}