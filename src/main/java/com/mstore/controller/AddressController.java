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


import com.mstore.dto.AddressDto;
import com.mstore.response.GeneralResponse;
import com.mstore.service.AddressService;



@RestController
@RequestMapping("/api")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	Logger log = LoggerFactory.getLogger(AddressController.class);
	
	@PostMapping("/address")
	public ResponseEntity<GeneralResponse> saveAddress(@RequestBody AddressDto addressDto){
		log.info("Adding new Address for user id="+addressDto.getUserId());
		GeneralResponse generalResponse = addressService.saveAddressOfUser(addressDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@PutMapping("/address")
	public ResponseEntity<GeneralResponse> updateAddress(@RequestBody AddressDto addressDto){
		log.info("Updating Address of user id="+addressDto.getUserId());
		
		GeneralResponse generalResponse = addressService.updateAddressOfUser(addressDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@GetMapping("addresses/user")
	public ResponseEntity<List<AddressDto>> findAllAddressOfUser(){
		log.info("Finding all address of user ");
		
		List<AddressDto> allAddressOfUser = addressService.findAllAddressOfUser();
		
		return new ResponseEntity<List<AddressDto>>(allAddressOfUser,HttpStatus.OK);
	}
	
	@DeleteMapping("/address/{addressId}")
	public ResponseEntity<GeneralResponse> deleteAddressById(@PathVariable Long addressId){
		log.info("Deleting Address by id="+addressId);
		GeneralResponse generalResponse = addressService.deleteAddressById(addressId);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);

	}
	
	@GetMapping("/address/user/{userId}")
	public ResponseEntity<AddressDto> findAddressOfUser(@PathVariable Long userId){
		log.info("Findig All the address of user id="+userId);
		
		AddressDto addressDto = addressService.findAddressById(userId);
		
		return new ResponseEntity<AddressDto>(addressDto,HttpStatus.OK);
	}
	
}
