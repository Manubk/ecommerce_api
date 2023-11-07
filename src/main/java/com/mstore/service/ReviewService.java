package com.mstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mstore.dto.ReviewDto;
import com.mstore.exception.ProductException;
import com.mstore.exception.UserNotFoundException;
//import com.mstore.model.Review;
import com.mstore.model.User;
import com.mstore.response.GeneralResponse;

@Service
public interface ReviewService {

	public GeneralResponse createReview(ReviewDto reviewDto) throws ProductException ,UserNotFoundException;
	
	public GeneralResponse updateReview(ReviewDto reviewDto) throws ProductException ,UserNotFoundException;
	
	public List<ReviewDto> getAllReviewByProduct(Long productId);
	
	public List<ReviewDto> getAllReviewByUser(Long userId);
}
