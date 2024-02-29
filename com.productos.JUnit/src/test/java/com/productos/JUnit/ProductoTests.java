package com.productos.JUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ProductoTests
{
	@Autowired
	private RepoProducto repoProducto;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarProducto()
	{
		Producto producto = new Producto("Laptop HP",2500);
		Producto productoGuardado = repoProducto.save(producto);
		
		assertNotNull(productoGuardado);
	}
	
	@Test
	@Order(2)
	public void testBuscarProductoPorNombre()
	{
		String nombre = "Laptop HP";
		Producto producto = repoProducto.findByNombre(nombre);
		
		assertThat(producto.getNombre()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testBuscarProductoPorNombreNoExistente()
	{
		String nombre = "Televisor Samsung HDD";
		Producto producto = repoProducto.findByNombre(nombre);
		
		assertNull(producto);
	}

	@Test
	@Rollback(false)
	@Order(4)
	public void testActualizarProducto()
	{
		String nombreProducto = "IPhone 17";
		Producto producto = new Producto(nombreProducto,5500);
		producto.setId(5);
		repoProducto.save(producto);

		Producto productoActualizado = repoProducto.findByNombre(nombreProducto);
		assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);
	}
	
	@Test
	@Order(5)
	public void testListarProductos()
	{
		List<Producto> productos = (List<Producto>) repoProducto.findAll();
		for(Producto producto : productos)
		{
			System.out.println(producto);
		}
		assertThat(productos).size().isGreaterThan(0);
	}

	@Test
	@Rollback(false)
	@Order(6)
	public void testEliminarProducto()
	{
		Integer id = 6;
		Boolean esExistenteAntesDeEliminar = repoProducto.findById(id).isPresent();
		repoProducto.deleteById(id);

		Boolean noExisteDespuesDeEliminar = repoProducto.findById(id).isPresent();
		assertTrue(esExistenteAntesDeEliminar);
		assertFalse(noExisteDespuesDeEliminar);
	}

}
