package com.mstore.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mstore.dto.ProductDto;
import com.mstore.exception.ProductException;
import com.mstore.response.GeneralResponse;

@Service
public interface ProductService {

	public GeneralResponse createProduct(ProductDto productDto);
	
	public GeneralResponse  deleteProductById(Long productId) throws ProductException;
	
	public GeneralResponse updateProduct(ProductDto productDto) throws ProductException;
	
	public ProductDto findProductById(Long productId) throws ProductException;
	
	public List<ProductDto> findProductsBySeller();
	
	public List<ProductDto> findProductByCategory(String Category) ;
	
	public Page<ProductDto> getAllProducts(List<String> category , List<String> colors,List<String> sizes,Double minPrice,
			Double maxPrice,Integer minDiscount, String sort , String stock , Integer pageNumber , Integer pageSize);
	
}
