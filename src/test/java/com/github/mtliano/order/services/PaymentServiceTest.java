package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.Payment;
import com.github.mtlibano.order.services.PaymentService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class PaymentServiceTest extends BaseTests {
	
	@Autowired
	PaymentService paymentService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var payment = paymentService.findById(1);
		assertThat(payment).isNotNull();
		assertEquals(1, payment.getId());
		assertEquals("Pix", payment.getType());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> paymentService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAllTest() {
		var list = paymentService.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> paymentService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	void insertTest() {
		Payment payment = paymentService.insert(new Payment(null, "PayPal"));
		assertThat(payment).isNotNull();
		var newPayment = paymentService.findById(1);
		assertEquals(1, newPayment.getId());
		assertEquals("PayPal", newPayment.getType());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Type")
	void insertErrorTypeTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> paymentService.insert(new Payment(null, null)));
		assertEquals("Tipo inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void updateTest() {
		paymentService.update(new Payment(5, "PayPal"));
		var payment = paymentService.findById(5);
		assertEquals("PayPal", payment.getType());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void deleteTest() {
		paymentService.delete(2);
		var list = paymentService.listAll();
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Tipo")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByTypeIgnoreCase() {
		var list = paymentService.findByTypeIgnoreCase("pix");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Tipo ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByTypeIgnoreCaseError() {
		var exception = assertThrows(ObjectNotFound.class, () -> paymentService.findByTypeIgnoreCase("paypal"));
		assertEquals("Nenhum pagamento cadastrado desse tipo: paypal", exception.getMessage());
	}

}