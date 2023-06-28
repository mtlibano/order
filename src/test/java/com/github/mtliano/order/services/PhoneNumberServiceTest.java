package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.PhoneNumber;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.PhoneNumberService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class PhoneNumberServiceTest extends BaseTests {
	
	@Autowired
	PhoneNumberService phoneNumberService;
	
	@Autowired
	ClientService clientService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var phoneNumber = phoneNumberService.findById(1);
		assertThat(phoneNumber).isNotNull();
		assertEquals(1, phoneNumber.getId());
		assertEquals("Max", phoneNumber.getClient().getName());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> phoneNumberService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAllTest() {
		var list = phoneNumberService.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> phoneNumberService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void insertTest() {
		PhoneNumber phoneNumber = phoneNumberService.insert(new PhoneNumber(null, "911223344", clientService.findById(3)));
		assertThat(phoneNumber).isNotNull();
		var newPhoneNumber = phoneNumberService.findById(1);
		assertEquals(1, newPhoneNumber.getId());
		assertEquals("911223344", newPhoneNumber.getPhoneNumber());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR número telefone")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void insertErrorPhoneNumberTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> phoneNumberService.insert(new PhoneNumber(null, null, clientService.findById(3))));
		assertEquals("Número telefone inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR cliente")
	@Sql({"classpath:/resources/sqls/client.sql"})
	void insertErrorClientTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> phoneNumberService.insert(new PhoneNumber(null, "911223344", null)));
		assertEquals("Cliente inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void updateTest() {
		phoneNumberService.update(new PhoneNumber(3, "911223344", clientService.findById(2)));
		var phoneNumber = phoneNumberService.findById(3);
		assertEquals("911223344", phoneNumber.getPhoneNumber());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void deleteTest() {
		phoneNumberService.delete(2);
		var list = phoneNumberService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por número telefone")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPhoneNumber() {
		var list = phoneNumberService.findByPhoneNumber("984762611");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por número telefone ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPhoneNumberError() {
		var exception = assertThrows(ObjectNotFound.class, () -> phoneNumberService.findByPhoneNumber("911223344"));
		assertEquals("Nenhum telefone cadastrado com esse número: 911223344", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por cliente")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByClient() {
		var list = phoneNumberService.findByClient(clientService.findById(1));
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por cliente ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByClientError() {
		var exception = assertThrows(ObjectNotFound.class, () -> phoneNumberService.findByClient(clientService.findById(3)));
		assertEquals("Nenhum telefone cadastrado para esse cliente: Mari", exception.getMessage());
	}

}