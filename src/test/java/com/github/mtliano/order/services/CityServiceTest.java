package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.City;
import com.github.mtlibano.order.services.CityService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class CityServiceTest extends BaseTests {
	
	@Autowired
	CityService cityService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var city = cityService.findById(1);
		assertThat(city).isNotNull();
		assertEquals(1, city.getId());
		assertEquals("Tubarão", city.getDescription());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAll() {
		var list = cityService.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	void insertTest() {
		City city = cityService.insert(new City(null, "São Paulo", "SP"));
		assertThat(city).isNotNull();
		var newCity = cityService.findById(1);
		assertEquals(1, newCity.getId());
		assertEquals("São Paulo", newCity.getDescription());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Descrição")
	void insertTestErrorNullDescription() {
		var exception = assertThrows(IntegrityViolation.class, () -> cityService.insert(new City(null, null, "SC")));
		assertEquals("Descrição inválida!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR UF")
	void insertTestErrorNullUf() {
		var exception = assertThrows(IntegrityViolation.class, () -> cityService.insert(new City(null, "Tubarão", null)));
		assertEquals("UF inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void updateTest() {
		cityService.update(new City(4, "Floripa", "SC"));
		var city = cityService.findById(4);
		assertEquals("Floripa", city.getDescription());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void deleteTest() {
		cityService.delete(2);
		var list = cityService.listAll();
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Buscar Descrição")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDescriptionIgnoreCase() {
		var list = cityService.findByDescriptionIgnoreCase("tubarão");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar Descrição ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDescriptionIgnoreCaseError() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.findByDescriptionIgnoreCase("joinville"));
		assertEquals("Nenhuma cidade cadastrada com essa descrição: joinville", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar UF")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByUfIgnoreCase() {
		var list = cityService.findByUfIgnoreCase("sc");
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Buscar UF ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByUfIgnoreCaseError() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.findByUfIgnoreCase("sp"));
		assertEquals("Nenhuma cidade cadastrada com essa UF: sp", exception.getMessage());
	}

}