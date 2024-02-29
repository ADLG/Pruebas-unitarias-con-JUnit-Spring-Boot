package com.productos.JUnit;

import org.springframework.data.repository.CrudRepository;

public interface RepoProducto extends CrudRepository<Producto, Integer>
{
	public Producto findByNombre(String nombre);
}
