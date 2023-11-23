package com.mstore.service;

import com.mstore.dto.RatingDto;
import com.mstore.response.GeneralResponse;

public interface RatingService {
	
	public GeneralResponse addRating(RatingDto ratingDto);
	
	public void setRatingToAllProduct();
	
}
