package com.mstore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.mstore.dto.ProductDto;
import com.mstore.exception.ProductException;
import com.mstore.exception.ProductNotFoundException;
import com.mstore.model.Category;
import com.mstore.model.Product;
import com.mstore.model.User;
import com.mstore.repo.CategoryRepo;
import com.mstore.repo.ProductRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.ProductService;
import com.mstore.util.ApplicationUtils;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public GeneralResponse createProduct(ProductDto productDto) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Creating a New Product ");
		
		Category category = categoryRepo.findByName(productDto.getCategory().trim());
		
		//Creating a New Category if the category doesnot exist
		if(category == null) {
			category = new Category();
			category.setName(productDto.getCategory().trim());
			category = categoryRepo.save(category);
		}
		
		//Creating a new product 
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		product.setSizes(productDto.getSizes());
		product.setCategory(category);
		product.setCreatedBy(logedInUser);
		
		Product savedProduct = productRepo.save(product);
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Product Created SuccessFull ;)").build();
	}

	@Override
	public GeneralResponse deleteProductById(Long productId) throws ProductException {
		log.info("Deleting a product id="+productId);
		
		try {
		productRepo.deleteById(productId);
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Product Deleted SuccessFull ;)").build();
		}catch (Exception e) {
			log.error("Exception in Deleting Product id="+productId);
			log.error(e.getMessage());
			throw new ProductNotFoundException();
		}
	}

	@Override
	public GeneralResponse updateProduct(ProductDto productDto) throws ProductException {
		log.info("Updating a product id="+productDto.getId());
		
		Optional<Product> productOption = productRepo.findById(productDto.getId());
		Product product = productOption.get();
			if(productOption.isPresent()) {
				product.copyProperty(productDto);
				productRepo.save(product);
				
				return new GeneralResponse.GeneralResposeBuilder().setMessage("Product Updated Successfull").setIsSuccess(true).build();
			}else {
				throw new ProductNotFoundException();
			}
	}

	@Override
	public ProductDto findProductById(Long productId) throws ProductNotFoundException {
		log.info("Finding a product by id="+productId);
		
		Optional<Product> productOption = productRepo.findById(productId);
		
		if(productOption.isPresent()) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(productOption.get(), productDto);
			productDto.setCategory(productOption.get().getCategory().getName());
		
			return productDto;
		}
	
		throw new ProductNotFoundException();
	}

	@Override
	public List<ProductDto> findProductByCategory(String Category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProductDto> getAllProducts(List<String> category, List<String> colors, List<String> sizes, Double minPrice,
			Double maxPrice, Integer maxDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDto> findProductsBySeller() {
		log.info("Finding the product By seller");
		User logedInUser = ApplicationUtils.getLogedInUser();
		
		List<Product> products = productRepo.findProductByCreatedBy(logedInUser);
		List<ProductDto> productDtos = new ArrayList<ProductDto>();

		products.forEach( product -> {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);

			productDtos.add(productDto);
		});
		
		return productDtos;
	}

}
