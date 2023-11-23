package com.mstore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.dto.PaymentInformationDto;
import com.mstore.exception.PaymentInformationNotFoundException;
import com.mstore.model.PayMentInformation;
import com.mstore.model.User;
import com.mstore.repo.PaymentInformationRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.PaymentInformationService;
import com.mstore.util.ApplicationUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentInformationServiceImpl implements PaymentInformationService{

	@Autowired
	private PaymentInformationRepo paymentInformationRepo;
	
	@Override
	public GeneralResponse savePaymentInforemation(PaymentInformationDto paymentInformationDto) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Saving new paymentInformation");
		
	    PayMentInformation payMentInformation = new PayMentInformation();
	    BeanUtils.copyProperties(paymentInformationDto, payMentInformation);
	    
	    //set PaymentInformation Details
	    payMentInformation.setUser(logedInUser);

	    PayMentInformation savedPaymentInformation = paymentInformationRepo.save(payMentInformation);
	    
	    if(savedPaymentInformation != null)
	    	return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Payment Information saved successfull ;)").build();
	    else
	    	return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(false).setMessage("Unable to save Payment Information ;(").build();
	}

	@Override
	public GeneralResponse updatePaymentInformaion(PaymentInformationDto paymentInformationDto)
			throws PaymentInformationNotFoundException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Updating the payment information");
		
		Optional<PayMentInformation> paymentInformationOpt = paymentInformationRepo.findById(paymentInformationDto.getId());
		
		if(paymentInformationOpt.isPresent()) {
			BeanUtils.copyProperties(paymentInformationDto, paymentInformationOpt.get());
			PayMentInformation savedPaymentInformation = paymentInformationRepo.save(paymentInformationOpt.get());
			
			if(savedPaymentInformation != null)
				return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Payment Information Updated SuccessFull ;)").build();
		}else {
//			throw new PaymentInformationNotFoundException();
		}
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(false).setMessage("Unable to update payment information ").build();
	}

	@Override
	public GeneralResponse deletePaymentInformation(Long id) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Deleting the Payment Information of User="+logedInUser.getEmail());
		
		List<PayMentInformation> paymentInformations = paymentInformationRepo.findByUser(logedInUser);
		
		paymentInformations.forEach(paymentInfo -> {
			if(paymentInfo.getId() == id) {
				paymentInformationRepo.deleteById(id);
			}
		});
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Payment Information Deleted SuccessFull ;)").build();
	}

	@Override
	public List<PaymentInformationDto> getPaymentInformationOfUser() {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Getting Payment Information list of the user");
		
		List<PayMentInformation> paymentInformations = paymentInformationRepo.findByUser(logedInUser);
		List<PaymentInformationDto> paymentInformationDtos = new ArrayList<PaymentInformationDto>();
		
		paymentInformations.forEach( payment -> {
			PaymentInformationDto paymentInformationDto = new PaymentInformationDto();
			BeanUtils.copyProperties(payment, paymentInformationDto);
			paymentInformationDtos.add(paymentInformationDto); 
		});
		
		return paymentInformationDtos;
	}

}
