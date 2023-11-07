package com.mstore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.dto.ProductDto;
import com.mstore.dto.ReviewDto;
import com.mstore.exception.ProductException;
import com.mstore.exception.ProductNotFoundException;
import com.mstore.exception.UserNotFoundException;
import com.mstore.model.Product;
import com.mstore.model.Review;
import com.mstore.model.User;
import com.mstore.repo.ProductRepo;
import com.mstore.repo.ReviewRepo;
import com.mstore.repo.UserRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.ReviewService;

@Service
public class ReviewServiceImple implements ReviewService{

	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	Logger log = LoggerFactory.getLogger(ReviewServiceImple.class);
	
	@Override
	public GeneralResponse createReview(ReviewDto reviewDto) throws ProductException, UserNotFoundException {
		log.info("Creating a Review for Product id="+reviewDto.getProductId());
		
		Optional<User> userOption = userRepo.findById(reviewDto.getUserId());
		
		if(userOption.isPresent()) {
			
			Optional<Product> productOption = productRepo.findById(reviewDto.getProductId());
			
			if(productOption.isPresent()) {
				Review review = new Review();
				BeanUtils.copyProperties(reviewDto, review);
				review.setProduct(productOption.get());
				review.setUser(userOption.get());
				
				return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Review Added Successfull ;)").build();
			}else {
				throw new ProductNotFoundException();
			}
		}else {
			throw new UserNotFoundException();
		}
	
	}

	@Override
	public List<ReviewDto> getAllReviewByProduct(Long productId) {
		log.info("Getting All the Reviews Of Product id="+productId);
		
		Optional<Product> productOption = productRepo.findById(productId);
		
		if(productOption.isPresent()) {
			List<Review> reviews = reviewRepo.findByProduct(productOption.get());
			List<ReviewDto> reviewDtos = new ArrayList<>();
			reviews.forEach(review ->{
				ReviewDto reviewDto = new ReviewDto();
				BeanUtils.copyProperties(review, reviewDto);
				reviewDtos.add(reviewDto);
			});
		}else {
			throw new ProductNotFoundException();
		}
		
		return null;
	}

	@Override
	public List<ReviewDto> getAllReviewByUser(Long userId) {
		log.info("Finding all the reviews of the user id="+userId);
		
		Optional<User> userOption = userRepo.findById(userId);
		
		if(userOption.isPresent()) {
			List<Review> reviews = reviewRepo.findByUser(userOption.get());
			List<ReviewDto> reviewDtos = new ArrayList<>();
			reviews.forEach(review ->{
				ReviewDto reviewDto = new ReviewDto();
				BeanUtils.copyProperties(review, reviewDto);
				reviewDtos.add(reviewDto);
			});
		}else {
			throw new UserNotFoundException();
		}
		
		return null;
	}

	@Override
	public GeneralResponse updateReview(ReviewDto reviewDto) throws ProductException, UserNotFoundException {
		log.info("Updating Review of Product id="+reviewDto.getProductId());
		
		Optional<Review> reviewOption = reviewRepo.findById(reviewDto.getId());
		
		if(reviewOption.isPresent()) {
			Review review = reviewOption.get();
			review.setReview(reviewDto.getReview());
			reviewRepo.save(review);
		}
		return null;
	}

}
