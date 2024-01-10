package org.dadak.market.dao;

import java.util.List;
import java.util.Map;

import org.dadak.market.model.Product;
import org.dadak.market.model.ProductImage;

public interface ProductDao {
	
	public int saveProduct(Product one);
	
	public int saveProductImage(ProductImage one);
	
	public Product findById(int id);
	
	public List<Product> findAllOrderByIdDesc();
	
	public List<Product> findSomeByPaging(Map<String, Object> one);
	
	public int update(Product one);
	
	public int countProduct(Map<String, Object> one);
}
