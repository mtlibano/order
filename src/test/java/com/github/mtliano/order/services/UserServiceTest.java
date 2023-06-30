package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.User;
import com.github.mtlibano.order.services.UserService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests {
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByIdTest() {
		var user = userService.findById(3);
		assertThat(user).isNotNull();
		assertEquals(3, user.getId());
		assertEquals("max@trier.com", user.getEmail());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void listAllTest() {
		var list = userService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void insertTest()	{
		userService.save(new User(null, "Mari", "mari@trier.com", "12345", "USER"));
		var lista = userService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Email já utilizado")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void insertTestErrorEmail()	{
		var exception = assertThrows(IntegrityViolation.class, () -> userService.save(new User(null, "UserTest", "max@trier.com", "12345", "ADMIN")));
		assertEquals("Email max@trier.com já foi utilizado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void updateTest()	{
		userService.update(new User(4, "Mari", "mari@trier.com", "12345", "USER"));
		var newUser = userService.findById(4);
		assertEquals("mari@trier.com", newUser.getEmail());
	}
	
	@Test
	@DisplayName("Update ERROR Email já utilizado")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void updateTestEmailError()	{
		var exception = assertThrows(IntegrityViolation.class, () -> userService.update(new User(4, "UserTest", "max@trier.com", "12345", "USER")));
		assertEquals("Email max@trier.com já foi utilizado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update ERROR ID inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void updateTestErrorNonExistent()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.update(new User(10, "UserTest", "user@trier.com", "12345", "USER")));
		assertEquals("ID 10 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void deleteTest()	{
		userService.delete(4);
		var lista = userService.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Delete ERROR ID inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void deleteNonExistentTest()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.delete(10));
		assertEquals("ID 10 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por Nome")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameUserTest()	{
		var lista = userService.findByNameIgnoreCase("max");
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar por Nome ERROR")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameUserTestError()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByNameIgnoreCase("maria"));
		assertEquals("Nenhum usuário cadastrado com esse nome: maria", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por Email")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByEmailIgnoreCaseTest()	{
		var lista = userService.findByEmailIgnoreCase("max@trier.com");
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca por Email ERROR")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByEmailIgnoreCaseTestError()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByEmailIgnoreCase("maria@trier.com"));
		assertEquals("Nenhum usuário cadastrado com esse email: maria@trier.com", exception.getMessage());
	}
	
}