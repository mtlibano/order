package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.Client;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.ClientService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class ClientServiceTest extends BaseTests {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	CityService cityService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var client = clientService.findById(1);
		assertThat(client).isNotNull();
		assertEquals(1, client.getId());
		assertEquals("Max", client.getName());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAllTest() {
		var list = clientService.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertTest() {
		Client client = clientService.insert(new Client(null, "Maria", "12345678912", "maria@trier.com", null, "88708600", "Rua1", "160", "São João", cityService.findById(1)));
		assertThat(client).isNotNull();
		var newClient = clientService.findById(1);
		assertEquals(1, newClient.getId());
		assertEquals("Maria", newClient.getName());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Nome")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertErrorNameTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(new Client(null, null, "12345678912", "maria@trier.com", null, "88708600", "Rua1", "160", "São João", cityService.findById(1))));
		assertEquals("Nome inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR CPF")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertErrorCpfTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(new Client(null, "Maria", null, "maria@trier.com", null, "88708600", "Rua1", "160", "São João", cityService.findById(1))));
		assertEquals("CPF inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Email")
	@Sql({"classpath:/resources/sqls/city.sql"})
	void insertErrorEmailTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(new Client(null, "Maria", "12345678912", null, null, "88708600", "Rua1", "160", "São João", cityService.findById(1))));
		assertEquals("Email inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void updateTest() {
		clientService.update(new Client(3, "Maria", "12345678912", "maria@trier.com", null, "88708600", "Rua1", "160", "São João", cityService.findById(1)));
		var client = clientService.findById(3);
		assertEquals("Maria", client.getName());
		assertEquals("São João", client.getDistrict());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void deleteTest() {
		clientService.delete(2);
		var list = clientService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Nome")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByNameIgnoreCase() {
		var list = clientService.findByNameIgnoreCase("max");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Nome ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByNameIgnoreCaseError() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByNameIgnoreCase("bruna"));
		assertEquals("Nenhum cliente cadastrado com esse nome: bruna", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por CPF")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByCpf() {
		Optional<Client> client = clientService.findByCpf("08124991901");
		assertEquals(1, client.get().getId());
		assertEquals("Max", client.get().getName());
	}
	
	@Test
	@DisplayName("Buscar por CPF ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByCpfError() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByCpf("12312312312"));
		assertEquals("Nenhum cliente cadastrado com esse CPF: 12312312312", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por email")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByEmail() {
		Optional<Client> client = clientService.findByEmailIgnoreCase("max@trier.com");
		assertEquals(1, client.get().getId());
		assertEquals("Max", client.get().getName());
	}
	
	@Test
	@DisplayName("Buscar por email ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByEmailError() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByEmailIgnoreCase("pedro@trier.com"));
		assertEquals("Nenhum cliente cadastrado com esse email: pedro@trier.com", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por data nascimento")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByBirthDate() {
		ZonedDateTime date = ZonedDateTime.of(1992, 10, 24, 0, 0, 0, 0, ZoneId.systemDefault());
		var list = clientService.findByBirthDate(date);
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por data nascimento ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByBirthDateError() {
		ZonedDateTime date = ZonedDateTime.of(2020, 10, 24, 0, 0, 0, 0, ZoneId.systemDefault());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByBirthDate(date));
		assertEquals("Nenhum cliente com essa data de nascimento: 2020-10-24T00:00-03:00[America/Sao_Paulo]", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por intervalo de data nascimento")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByBirthDateBetween() {
		ZonedDateTime initialDate = ZonedDateTime.of(1990, 10, 24, 0, 0, 0, 0, ZoneId.systemDefault());
		ZonedDateTime finalDate = ZonedDateTime.of(1998, 10, 24, 0, 0, 0, 0, ZoneId.systemDefault());
		var list = clientService.findByBirthDateBetween(initialDate, finalDate);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por intervalo de data nascimento ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByBirthDateBetweenError() {
		ZonedDateTime initialDate = ZonedDateTime.of(1980, 10, 24, 0, 0, 0, 0, ZoneId.systemDefault());
		ZonedDateTime finalDate = ZonedDateTime.of(1990, 10, 24, 0, 0, 0, 0, ZoneId.systemDefault());
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByBirthDateBetween(initialDate, finalDate));
		assertEquals("Nenhum cliente nesse intervalo de data de nascimento: 1980-10-24T00:00-03:00[America/Sao_Paulo] e 1990-10-24T00:00-02:00[America/Sao_Paulo]", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por bairro")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDistrictIgnoreCase() {
		var list = clientService.findByDistrictIgnoreCase("bairro2");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por bairro ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDistrictIgnoreCaseError() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findByDistrictIgnoreCase("bairro4"));
		assertEquals("Nenhum cliente cadastrado com esse bairro: bairro4", exception.getMessage());
	}

}