package com.mstore.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.dto.ReviewDto;
import com.mstore.exception.ProductException;
import com.mstore.exception.UserNotFoundException;
import com.mstore.response.GeneralResponse;
import com.mstore.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	Logger log = LoggerFactory.getLogger(ReviewController.class);

	@GetMapping("/{productId}")
	public ResponseEntity<List<ReviewDto>> findAllReviewOfProduct(@PathVariable Long productId) {
		log.info("Finding Reviews of product id =" + productId);

		List<ReviewDto> allReviews = reviewService.getAllReviewByProduct(productId);

		return new ResponseEntity<List<ReviewDto>>(allReviews, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<ReviewDto>> findAllReviewByUser() {
		log.info("Finding All Reviews By User ");

		List<ReviewDto> allReviews = reviewService.getAllReviewByUser();

		return new ResponseEntity<List<ReviewDto>>(allReviews, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<GeneralResponse> saveReview(@RequestBody ReviewDto reviewDto)
			throws UserNotFoundException, ProductException {
		log.info("Saving Review to Product id=" + reviewDto.getProductId());

		GeneralResponse generalResponse = reviewService.createReview(reviewDto);

		return new ResponseEntity<GeneralResponse>(generalResponse, HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<GeneralResponse> updateReview(@RequestBody ReviewDto reviewDto)
			throws UserNotFoundException, ProductException {
		log.info("Saving Review to Product id=" + reviewDto.getProductId());

		GeneralResponse generalResponse = reviewService.updateReview(reviewDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<GeneralResponse> deleteReview(@PathVariable Long reviewId){
		log.info("Deleting the Review by id="+reviewId);
		
		GeneralResponse generalResponse = reviewService.deleteReview(reviewId);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
		
	}
	
}
