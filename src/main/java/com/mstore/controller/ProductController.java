package com.mstore.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.dto.ProductDto;
import com.mstore.exception.ProductException;
import com.mstore.exception.ProductNotFoundException;
import com.mstore.response.GeneralResponse;
import com.mstore.service.ProductService;

@PreAuthorize(value = "ROLE_SELLER")
@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	Logger log = LoggerFactory.getLogger(ProductController.class);
	
//	@PreAuthorize("hasRole('ADMIN') or haseRole('SELLER')")
	@PostMapping()
	public ResponseEntity<GeneralResponse> saveProduct(@RequestBody ProductDto productDto){
		log.info("Creating a Product ");
		
		GeneralResponse generalResponse = productService.createProduct(productDto);
		
		return new  ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.CREATED);
	}
	
	@PutMapping()
	public ResponseEntity<GeneralResponse> updateProduct(@RequestBody ProductDto productDto) throws ProductException{
		log.info("Updating a Product");
		
	    GeneralResponse generalResponse = productService.updateProduct(productDto);
	    
	    return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> findProductById(@PathVariable Long productId) throws ProductException{
		log.info("Finding a Product By id="+productId);
		
		ProductDto productDto = productService.findProductById(productId);
		
		return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<GeneralResponse> deleteProductById(@PathVariable Long productId) throws ProductNotFoundException, ProductException{
		log.info("Deleting The Product By id"+productId);
		
		GeneralResponse generalResponse = productService.deleteProductById(productId);
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<ProductDto>> findProductsBySeller(){
		log.info("Finding the Products By seller");
		
		List<ProductDto> productDtos = productService.findProductsBySeller();
		return new ResponseEntity<>(productDtos,HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<ProductDto>> findFilteredProduct(@PathVariable(name = "minPrice")Double minPrice , @PathVariable(name = "maxPrice")Double maxPrice
			,@PathVariable(name = "colour")List<String> colour, @PathVariable(name = "brand")List<String> brand,@PathVariable(name = "size")List<String> size,@PathVariable(name = "category")List<String> category
			,@PathVariable(name = "rating")Float rating,@PathVariable(name = "mazDiscount")int maxDiscount,@PathVariable(name = "sort")boolean sort,@PathVariable(name = "pageNum")int pageNum
			,@PathVariable(name = "pageSize")int pageSize,@PathVariable(name = "stock")boolean stock){
		
//		productService.getAllProducts(category, colour, size, minPrice, maxPrice, maxDiscount, sort, stock, pageNum, pageSize);
		
		return null;
	}
	
	

}
