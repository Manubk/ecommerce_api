package com.mstore.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.dto.RatingDto;
import com.mstore.exception.ProductNotFoundException;
import com.mstore.model.Product;
import com.mstore.model.Rating;
import com.mstore.model.User;
import com.mstore.repo.ProductRepo;
import com.mstore.repo.RatingRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.RatingService;
import com.mstore.util.ApplicationUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RatingServiceImpl  implements RatingService{

	@Autowired
	private RatingRepo ratingRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Override
	public GeneralResponse addRating(RatingDto ratingDto) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Adding Rating ");
		
		Optional<Product> productOpt = productRepo.findById(ratingDto.getProductId());
		
		if(productOpt.isEmpty())
			throw new ProductNotFoundException();
		
		Rating rating = new Rating();
		rating.setProduct(productOpt.get());
		ratingRepo.save(rating);
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Rating added successfull ;)").build();
	}

	//this method is a batch processing to create a final rating for product 
	@Override
	public void setRatingToAllProduct() {
		log.info("Adding Rating to products batch product");
		
		List<Product> products = productRepo.findAll();
		
		products.forEach(product -> {
	
			 List<Rating> ratings = ratingRepo.findByProduct(product);
			 Double rat =0d;
			 Long length = 0l;
			 
			 //Calculating the rating
			for(Rating rating : ratings) {
				rat = rat+rating.getRating();
				length++;
			}
			
			float finalRat = (float) (rat/length);
			product.setRatings(finalRat);
			
			//persisting the data
			productRepo.save(product);
			
		});
		
	}



}
