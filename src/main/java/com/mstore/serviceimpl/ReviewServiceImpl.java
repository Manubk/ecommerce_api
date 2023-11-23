package com.mstore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.dto.ReviewDto;
import com.mstore.exception.ProductException;
import com.mstore.exception.ProductNotFoundException;
import com.mstore.exception.ReviewNotFoundException;
import com.mstore.exception.UserNotFoundException;
import com.mstore.model.Product;
import com.mstore.model.Review;
import com.mstore.model.User;
import com.mstore.repo.ProductRepo;
import com.mstore.repo.ReviewRepo;
import com.mstore.repo.UserRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.ReviewService;
import com.mstore.util.ApplicationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	
	@Override
	public GeneralResponse createReview(ReviewDto reviewDto) throws ProductException, UserNotFoundException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Creating a Review for Product id="+reviewDto.getProductId());
			
			Optional<Product> productOption = productRepo.findById(reviewDto.getProductId());
			
			if(productOption.isPresent()) {
				Review review = new Review();
				BeanUtils.copyProperties(reviewDto, review);
				review.setProduct(productOption.get());
				review.setUser(logedInUser);
				
				return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Review Added Successfull ;)").build();
			}else {
				throw new ProductNotFoundException();
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
	public GeneralResponse updateReview(ReviewDto reviewDto) throws ProductException, UserNotFoundException {
		log.info("Updating Review of Product id="+reviewDto.getProductId());
		
		Optional<Review> reviewOption = reviewRepo.findById(reviewDto.getId());
		
		if(reviewOption.isPresent()) {
			Review review = reviewOption.get();
			review.setReview(reviewDto.getReview());
			reviewRepo.save(review);
		}
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Review Saved Successfull ;)").build();
	}

	@Override
	public List<ReviewDto> getAllReviewByUser() {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Getting all the review by users");
		
		List<Review> reviews = reviewRepo.findByUser(logedInUser);
		List<ReviewDto> reviewDtos = new ArrayList<ReviewDto>();
		
		reviews.forEach(review -> {
			ReviewDto reviewDto = new ReviewDto();
			BeanUtils.copyProperties(review, reviewDto);
			
			reviewDtos.add(reviewDto);
		});
		
		return reviewDtos;
	}

	@Override
	public GeneralResponse deleteReview(Long reviewId) throws ReviewNotFoundException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Deleting the Review id="+reviewId);
		
		Optional<Review> reviewOpt = reviewRepo.findById(reviewId);
		
		if(reviewOpt.isPresent()) {
			User user = reviewOpt.get().getUser();
			if(user.getId() == logedInUser.getId()) {
				reviewRepo.delete(reviewOpt.get());
			}else {
				return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(false).setMessage("Review doesn't belongs to this user").build();
			}
		}else {
			throw new ReviewNotFoundException("Review Not Found");
		}
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Review Deleted Successfull ;)").build();
	}

}
