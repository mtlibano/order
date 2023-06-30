package com.github.mtliano.order.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtlibano.order.OrderApplication;
import com.github.mtlibano.order.config.jwt.LoginDTO;
import com.github.mtlibano.order.domain.dto.UserDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST, 
				requestEntity, 
				String.class);
		
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("max@trier.com", "qwerty")),
				UserDTO.class
				);
	}
	
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("max@trier.com", "qwerty")),
				new ParameterizedTypeReference<List<UserDTO>>() {}
				);
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void createTest() {
		UserDTO dto = new UserDTO(null, "UserName", "UserEmail", "UserPassword", "ADMIN");
		HttpHeaders headers = getHeaders("max@trier.com", "qwerty");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/users",
				HttpMethod.POST,
				requestEntity,
				UserDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		UserDTO user = responseEntity.getBody();
		assertEquals("UserName", user.getName());
	}
	
	@Test
	@DisplayName("Update")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void updateTest() {
		UserDTO dto = new UserDTO(4, "UserName", "UserEmail", "UserPassword", "ADMIN");
		HttpHeaders headers = getHeaders("max@trier.com","qwerty");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/users/4",
				HttpMethod.PUT,
				requestEntity,
				UserDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		UserDTO user = responseEntity.getBody();
		assertEquals("UserName", user.getName());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void deleteTest() {
		HttpHeaders headers = getHeaders("max@trier.com","qwerty");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/users/3",
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void findByIdTest() {
		ResponseEntity<UserDTO> response = getUser("/users/3");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		UserDTO user = response.getBody();
		assertEquals("Max", user.getName());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void listAllTest() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange(
				"/users",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders("max@trier.com","qwerty")),
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(2, responseEntity.getBody().size());
	}
	
	@Test
	@DisplayName("Buscar por Nome")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void findByNameTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/users/name/niki");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Niki", response.getBody().get(0).getName());
	}
	
	@Test
	@DisplayName("Buscar por Email")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void findByEmailTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/users/email/niki@trier.com");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Niki", response.getBody().get(0).getName());
	}

}