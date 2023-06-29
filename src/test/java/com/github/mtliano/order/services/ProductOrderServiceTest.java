package com.github.mtliano.order.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.github.mtliano.order.BaseTests;
import com.github.mtlibano.order.domain.ProductOrder;
import com.github.mtlibano.order.services.OrderService;
import com.github.mtlibano.order.services.ProductOrderService;
import com.github.mtlibano.order.services.ProductService;
import com.github.mtlibano.order.services.exceptions.IntegrityViolation;
import com.github.mtlibano.order.services.exceptions.ObjectNotFound;

import jakarta.transaction.Transactional;

@Transactional
public class ProductOrderServiceTest extends BaseTests {
	
	@Autowired
	ProductOrderService productOrderService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	@Test
	@DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTest() {
		var productOrder = productOrderService.findById(1);
		assertThat(productOrder).isNotNull();
		assertEquals(1, productOrder.getId());
		assertEquals(4, productOrder.getQuantity());
	}
	
	@Test
	@DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByIdTestError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productOrderService.findById(100));
		assertEquals("ID 100 não encontrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void listAll() {
		var list = productOrderService.listAll();
		assertEquals(8, list.size());
	}
	
	@Test
	@DisplayName("Listar todos ERROR Void")
	void listAllError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productOrderService.listAll());
		assertEquals("Void!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql({"classpath:/resources/sqls/product_order.sql"})
	void insert() {
		var productOrder = productOrderService.insert(new ProductOrder(null, 5, productService.findById(1), orderService.findById(2)));
		assertThat(productOrder).isNotNull();
		var newProductOrder = productOrderService.findById(1);
		assertEquals(1, newProductOrder.getId());
		assertEquals(5, newProductOrder.getQuantity());
		assertEquals("Martelo", newProductOrder.getProduct().getDescription());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Quantidade")
	@Sql({"classpath:/resources/sqls/product_order.sql"})
	void insertErrorQuantityTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> productOrderService.insert(new ProductOrder(null, null, productService.findById(1), orderService.findById(2))));
		assertEquals("Quantidade inválida!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Produto")
	@Sql({"classpath:/resources/sqls/product_order.sql"})
	void insertErrorProdutoTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> productOrderService.insert(new ProductOrder(null, 5, null, orderService.findById(2))));
		assertEquals("Produto inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Cadastrar ERROR Pedido")
	@Sql({"classpath:/resources/sqls/product_order.sql"})
	void insertErrorPaymentTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> productOrderService.insert(new ProductOrder(null, 5, productService.findById(1), null)));
		assertEquals("Pedido inválido!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void update() {
		productOrderService.update(new ProductOrder(8, 4, productService.findById(2), orderService.findById(4)));
		var productOrder = productOrderService.findById(8);
		assertEquals(4, productOrder.getQuantity());
	}
	
	@Test
	@DisplayName("Delete")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void delete() {
		productOrderService.delete(2);
		var list = productOrderService.listAll();
		assertEquals(7, list.size());
	}
	
	@Test
	@DisplayName("Buscar Produto")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByProduct() {
		var list = productOrderService.findByProduct(productService.findById(1));
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Buscar Produto ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByProductError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productOrderService.findByProduct(productService.findById(6)));
		assertEquals("Nenhum PedidoProduto com esse produto: Serra!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Buscar Pedido")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByOrder() {
		var list = productOrderService.findByOrder(orderService.findById(2));
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Buscar Pedido ERROR")
	@Sql({"classpath:/resources/sqls/order_all.sql"})
	void findByOrderError() {
		var exception = assertThrows(ObjectNotFound.class, () -> productOrderService.findByOrder(orderService.findById(5)));
		assertEquals("Nenhum PedidoProduto com esse pedido: 5!", exception.getMessage());
	}

}