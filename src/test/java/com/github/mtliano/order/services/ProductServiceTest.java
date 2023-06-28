package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.Product;
import com.github.mtlibano.order.services.ProductService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class ProductServiceTest extends BaseTests {
	
	@Autowired
	ProductService productService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var product = productService.findById(1);
		assertThat(product).isNotNull();
		assertEquals(1, product.getId());
		assertEquals("Martelo", product.getDescription());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAllTest() {
		var list = productService.listAll();
		assertEquals(6, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	void insertTest() {
		Product product = productService.insert(new Product(null, "Marreta", new BigDecimal(6.66), "1234567891012"));
		assertThat(product).isNotNull();
		var newProduct = productService.findById(1);
		assertEquals(1, newProduct.getId());
		assertEquals("Marreta", newProduct.getDescription());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR descrição")
	void insertErrorDescriptionTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(new Product(null, null, new BigDecimal(6.66), "1234567891012")));
		assertEquals("Descrição inválida!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR preço")
	void insertErrorPriceTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(new Product(null, "Marreta", null, "1234567891012")));
		assertEquals("Preço inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void updateTest() {
		productService.update(new Product(6, "Marreta", new BigDecimal(6.66), "1234567891012"));
		var product = productService.findById(6);
		assertEquals("Marreta", product.getDescription());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void deleteTest() {
		productService.delete(2);
		var list = productService.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Buscar por descrição")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDescriptionIgnoreCase() {
		var list = productService.findByDescriptionIgnoreCase("martelo");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por descrição ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByDescriptionIgnoreCaseError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.findByDescriptionIgnoreCase("test"));
		assertEquals("Nenhum produto cadastrado com essa descrição: test", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por preço")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPrice() {
		var list = productService.findByPrice(new BigDecimal(10));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Buscar por preço ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPriceError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.findByPrice(new BigDecimal(40)));
		assertEquals("Nenhum produto cadastrado com esse preço: 40", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar por intervalo de preço")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPriceBetween() {
		var list = productService.findByPriceBetween(new BigDecimal(10), new BigDecimal(40));
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Buscar por intervalo de preço ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByPriceBetweemError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.findByPriceBetween(new BigDecimal(50), new BigDecimal(60)));
		assertEquals("Nenhum produto nessa faixa de preço: 50 e 60", exception.getMessage());
	}

}