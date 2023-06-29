package com.github.mtliano.order.resources;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.github.mtlibano.order.domain.dto.ProductDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<ProductDTO> getProduct(String url) {
		return rest.getForEntity(url, ProductDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<ProductDTO>> getProductList(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDTO>>() {});
	}
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testGetId() {
		ResponseEntity<ProductDTO> response = getProduct("/product/1");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ProductDTO product = response.getBody();
		assertEquals(1, product.getId());
		assertEquals("Martelo", product.getDescription());
		assertEquals(new BigDecimal("10.00"), product.getPrice());
	}

	@Test
	@DisplayName("Buscar por ID inexistente")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testGetIdNotFound() {
		ResponseEntity<ProductDTO> response = getProduct("/product/100");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testListAll() {
		ResponseEntity<List<ProductDTO>> responseEntity = rest.exchange(
				"/product",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<ProductDTO> list = responseEntity.getBody();
	    assertEquals(6, list.size());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	public void testCreateProduct() {
		ProductDTO product = new ProductDTO(null, "Parafusadeira", new BigDecimal("20"), "1234567891020");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product, headers);
		ResponseEntity<ProductDTO> responseEntity = rest.exchange(
				"/product",
				HttpMethod.POST,
				requestEntity,
				ProductDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ProductDTO newProduct = responseEntity.getBody();
		assertEquals("Parafusadeira", newProduct.getDescription());
		assertEquals("1234567891020", newProduct.getBarcode());
	}
	
	@Test
	@DisplayName("Update")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public  void testUpdate() {	    
		ProductDTO product = new ProductDTO(5, "Parafusadeira", new BigDecimal("20"), "1234567891020");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProductDTO> requestEntity = new HttpEntity<>(product, headers);
		ResponseEntity<ProductDTO> responseEntity = rest.exchange(
				"/product/5",
                HttpMethod.PUT,
                requestEntity,
                ProductDTO.class
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ProductDTO updatedProduct = responseEntity.getBody();
		assertThat(updatedProduct).isNotNull();
		assertEquals(5, updatedProduct.getId());
		assertEquals("Parafusadeira", updatedProduct.getDescription());
		assertEquals(new BigDecimal("20"), updatedProduct.getPrice());
	}
	
	@Test
	@DisplayName("Delete por ID")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testDeleteId() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/product/1", 
				HttpMethod.DELETE,  
				requestEntity, 
				Void.class
				);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	@DisplayName("Buscar por Descrição")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testFindByDescriptionIgnoreCase() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<ProductDTO>> responseEntity = rest.exchange(
				"/product/description/martelo",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<ProductDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Martelo", list.get(0).getDescription());
	}
	
	@Test
	@DisplayName("Buscar por Preço")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testFindByPrice() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<ProductDTO>> responseEntity = rest.exchange(
				"/product/price/10",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<ProductDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Martelo", list.get(0).getDescription());
	}
	
	@Test
	@DisplayName("Buscar por intervalo Preço")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testFindByPriceBetween() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<ProductDTO>> responseEntity = rest.exchange(
				"/product/price/10/20",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<ProductDTO> list = responseEntity.getBody();
	    assertEquals(3, list.size());
	    assertEquals(5, list.get(1).getId());
	    assertEquals("Serrote", list.get(1).getDescription());
	}
	
	@Test
	@DisplayName("Buscar por Código de Barra")
	@Sql(scripts = "classpath:/resources/sqls/clean_table.sql")
	@Sql(scripts = "classpath:/resources/sqls/product.sql")
	public void testFindByBarcode() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<ProductDTO>> responseEntity = rest.exchange(
				"/product/barcode/1234567891012",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<ProductDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Martelo", list.get(0).getDescription());
	}

}