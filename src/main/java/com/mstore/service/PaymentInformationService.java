package com.mstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mstore.dto.PaymentInformationDto;
import com.mstore.exception.PaymentInformationNotFoundException;
import com.mstore.response.GeneralResponse;

@Service
public interface PaymentInformationService {

	public GeneralResponse savePaymentInforemation(PaymentInformationDto paymentInformationDto);
	
	public GeneralResponse updatePaymentInformaion(PaymentInformationDto paymentInformationDto) throws PaymentInformationNotFoundException;
	
	public GeneralResponse deletePaymentInformation(Long id);
	
	public List<PaymentInformationDto> getPaymentInformationOfUser(); 
}
