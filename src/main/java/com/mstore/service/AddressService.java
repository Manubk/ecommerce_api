package com.mstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mstore.dto.AddressDto;
import com.mstore.response.AuthResponse;
import com.mstore.response.GeneralResponse;

@Service
public interface AddressService {

	public List<AddressDto> findAllAddressOfUser();
	
	public GeneralResponse updateAddressOfUser(AddressDto addressDto);
	
	public GeneralResponse deleteAddressById(Long addressId);
	
	public AddressDto findAddressById(Long addressId);
	
	public GeneralResponse saveAddressOfUser(AddressDto addressDto);
	
}
