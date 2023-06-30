package com.github.mtliano.order.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
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
import com.github.mtlibano.order.domain.dto.ProductDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductResourceTest {
	
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
	
	private ResponseEntity<ProductDTO> getProduct(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("max@trier.com", "qwerty")),
				ProductDTO.class
				);
	}
	
	private ResponseEntity<List<ProductDTO>> getProducts(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("max@trier.com", "qwerty")),
				new ParameterizedTypeReference<List<ProductDTO>>() {}
				);
	}
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void getIdTest() {
		ResponseEntity<ProductDTO> response = getProduct("/product/1");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ProductDTO product = response.getBody();
		assertEquals("Martelo", product.getDescription());
		assertEquals(new BigDecimal("10.00"), product.getPrice());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void listAllTest() {
		ResponseEntity<List<ProductDTO>> responseEntity = rest.exchange(
				"/product",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders("max@trier.com","qwerty")),
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(6, responseEntity.getBody().size());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	public void createTest() {		
		ProductDTO product = new ProductDTO(null, "Parafusadeira", new BigDecimal("20"), "1234567891020");
		HttpHeaders headers = getHeaders("max@trier.com", "qwerty");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product, headers);
		ResponseEntity<ProductDTO> responseEntity = rest.exchange(
				"/product",
				HttpMethod.POST,
				requestEntity,
				ProductDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ProductDTO newProduct = responseEntity.getBody();
		assertEquals("Parafusadeira", newProduct.getDescription());
	}
	
	@Test
	@DisplayName("Update")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public  void updateTest() {	 
		ProductDTO product = new ProductDTO(5, "Parafusadeira", new BigDecimal("20"), "1234567891020");
		HttpHeaders headers = getHeaders("max@trier.com","qwerty");
		HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product, headers);
		ResponseEntity<ProductDTO> responseEntity = rest.exchange(
				"/product/5",
				HttpMethod.PUT,
				requestEntity,
				ProductDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ProductDTO newProduct = responseEntity.getBody();
		assertEquals("Parafusadeira", newProduct.getDescription());
		assertEquals("1234567891020", newProduct.getBarcode());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void deleteTest() {
		HttpHeaders headers = getHeaders("max@trier.com","qwerty");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/product/6",
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Buscar por Descrição")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void findByDescriptionIgnoreCaseTest() {
		ResponseEntity<List<ProductDTO>> response = getProducts("/product/description/martelo");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Martelo", response.getBody().get(0).getDescription());
	}
	
	@Test
	@DisplayName("Buscar por Preço")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void findByPriceTest() {
		ResponseEntity<List<ProductDTO>> response = getProducts("/product/price/10");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	    assertEquals("Martelo", response.getBody().get(0).getDescription());
	}
	
	@Test
	@DisplayName("Buscar por intervalo Preço")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void findByPriceBetweenTest() {
		ResponseEntity<List<ProductDTO>> response = getProducts("/product/price/10/20");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(3, response.getBody().size());
	    assertEquals("Serrote", response.getBody().get(1).getDescription());
	}
	
	@Test
	@DisplayName("Buscar por Código de Barra")
	@Sql(scripts = "classpath:/resources/sqls/erase_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/users.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void findByBarcodeTest() {
		ResponseEntity<List<ProductDTO>> response = getProducts("/product/barcode/1234567891012");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	    assertEquals("Martelo", response.getBody().get(0).getDescription());
	}

}