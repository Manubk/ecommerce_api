package com.mstore.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mstore.dto.ProductDto;
import com.mstore.exception.ProductException;

@Service
public interface ProductService {

	public ProductDto createProduct(ProductDto productDto);
	
	public String deleteProductById(Integer productId) throws ProductException;
	
	public ProductDto updateProduct(ProductDto productDto) throws ProductException;
	
	public ProductDto findProductById(Integer productId) throws ProductException;
	
	public List<ProductDto> findProductByCategory(String Category) ;
	
	public Page<ProductDto> getAllProducts(String category , List<String> colors,List<String> sizes,Integer minPrice,
			Integer maxPrice,Integer minDiscount, String sort , String stock , Integer pageNumber , Integer pageSize);
	
}
