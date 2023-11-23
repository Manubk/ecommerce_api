package com.mstore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.dto.AddressDto;
import com.mstore.exception.AddressNotFoundException;
import com.mstore.exception.UserException;
import com.mstore.model.Address;
import com.mstore.model.User;
import com.mstore.repo.AddressRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.AddressService;
import com.mstore.util.ApplicationUtils;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressRepo addressRepo;
	
	Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
	
	@Override
	public List<AddressDto> findAllAddressOfUser() {
		User currentUser = ApplicationUtils.getLogedInUser();
		log.info("Getting all address of the user id="+currentUser.getId());
	
		List<Address> addresses = addressRepo.findByUser(currentUser);
		List<AddressDto> addresDtos = new ArrayList<AddressDto>();
		
		addresses.stream().forEach( address ->{
		 AddressDto addressDto = new AddressDto();
		 BeanUtils.copyProperties(address, addressDto);
		 addresDtos.add(addressDto);
		});
		
		return addresDtos;
	}

	@Override
	public GeneralResponse updateAddressOfUser(AddressDto addressDto) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Updating address id="+addressDto.getId());
		
		List<Address> addresses = logedInUser.getAddress();
		
		addresses.stream().forEach(address -> {
			if(address.getId() == addressDto.getId()) {
				BeanUtils.copyProperties(addressDto, address);
				addressRepo.save(address);
			}
		});
			
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
					.setMessage("Address Updated SuccessFull ;)").build();

	}

	@Override
	public GeneralResponse deleteAddressById(Long addressId) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Deleting Address By id="+addressId+" for User="+logedInUser.getEmail());
		
		try {
			 addressRepo.deleteAddressOfUser(addressId, logedInUser);
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
					.setMessage("Address Deleted SuccessFul ;)").build();
			
		} catch (Exception e) {
			log.error("Exception in Deleting Address id="+addressId+" msg="+e.getMessage());
			throw new AddressNotFoundException("Address Not Found ;(");
		}
		
	}

	@Override
	public AddressDto findAddressById(Long addressId) {
		log.info("Getting the address by id="+addressId);
		
		Optional<Address> addressOption = addressRepo.findById(addressId);
		
		if(addressOption.isPresent()) {
			AddressDto addressDto = new AddressDto();
			BeanUtils.copyProperties(addressOption.get(), addressDto);
			return addressDto;
		}
		
		throw new AddressNotFoundException("Address Not Found ;(");
	}

	@Override
	public GeneralResponse saveAddressOfUser(AddressDto addressDto) {
		log.info("Adding Address to User id="+addressDto.getUserId());
		
		try{
		
			User currentUser = ApplicationUtils.getLogedInUser();
			if(currentUser != null) {
			Address address = new Address();
			BeanUtils.copyProperties(addressDto, address);
		    address.setUser(currentUser);
		    addressRepo.save(address);	

			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Address Added Successful ;)").build();	
			}
		
			throw new UserException("Wrong User ");

		}catch(Exception e){
			log.error("Error in saving the Address ");
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(false).setMessage("Unable to add Address ;(").build();	
		}
	
	}

}
