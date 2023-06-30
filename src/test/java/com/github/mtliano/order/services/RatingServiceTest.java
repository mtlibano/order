package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.Rating;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.RatingService;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class RatingServiceTest extends BaseTests {
	
	@Autowired
	RatingService ratingService;
	
	@Autowired
	OrderService orderService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var rating = ratingService.findById(1);
		assertThat(rating).isNotNull();
		assertEquals(1, rating.getId());
		assertEquals("Comment1", rating.getComment());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> ratingService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAll() {
		var list = ratingService.listAll();
		assertEquals(6, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllError() {
		var exception = assertThrows(ObjectNotFound.class, () -> ratingService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	void insert() {
		Rating rating = ratingService.insert(new Rating(null, 5, "Comment10"));
		assertThat(rating).isNotNull();
		var newRating = ratingService.findById(1);
		assertEquals(1, newRating.getId());
		assertEquals(5, newRating.getGrade());
		assertEquals("Comment10", newRating.getComment());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void update() {
		ratingService.update(new Rating(4, 1, "Comment10"));
		var rating = ratingService.findById(4);
		assertEquals("Comment10", rating.getComment());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/rating.sql"})
	void delete() {
		ratingService.delete(2);
		var list = ratingService.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Buscar Nota")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByGrade() {
		var list = ratingService.findByGrade(2);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar Nota ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByGradeError() {
		var exception = assertThrows(ObjectNotFound.class, () -> ratingService.findByGrade(0));
		assertEquals("Nenhuma avaliação com essa nota: 0", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar Intervalo de Nota")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByGradeBetween() {
		var list = ratingService.findByGradeBetween(1, 3);
		assertEquals(6, list.size());
	}
	
	@Test
	@DisplayName("Buscar Intervalo de Nota ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByGradeBetweenError() {
		var exception = assertThrows(ObjectNotFound.class, () -> ratingService.findByGradeBetween(4, 5));
		assertEquals("Nenhuma avaliação nesse intervalo de nota: 4 e 5", exception.getMessage());
	}

}