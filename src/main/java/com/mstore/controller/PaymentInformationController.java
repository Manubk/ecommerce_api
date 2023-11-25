package com.mstore.controller;

import java.util.List;

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

import com.mstore.dto.PaymentInformationDto;
import com.mstore.response.GeneralResponse;
import com.mstore.service.PaymentInformationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/")
public class PaymentInformationController {
	
	@Autowired
	private PaymentInformationService paymentInformationService;
	
	@PostMapping("paymentinfo")
	public ResponseEntity<GeneralResponse> savePaymentInformation(@RequestBody PaymentInformationDto paymentInformationDto) {
		log.info("Saving the New Payment Information ");
		
		GeneralResponse generalResponse = paymentInformationService.savePaymentInforemation(paymentInformationDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	} 
	
	@PutMapping("paymentinfo")
	public ResponseEntity<GeneralResponse> updatePaymentInformation(@RequestBody PaymentInformationDto paymentInformationDto){
		log.info("Updating new payment information id="+paymentInformationDto.getId());
		
		GeneralResponse generalResponse = paymentInformationService.updatePaymentInformaion(paymentInformationDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}

	@GetMapping("paymentinfos")
	public ResponseEntity<List<PaymentInformationDto>> getAllPaymentInfoOfUser(){
		log.info("Get All PaymentInfo of User");
		
		List<PaymentInformationDto> paymentInformations = paymentInformationService.getPaymentInformationOfUser();
		
		return new ResponseEntity<List<PaymentInformationDto>>(paymentInformations,HttpStatus.OK);
	}
	
	@DeleteMapping("paymentinfo/{paymentId}")
	public ResponseEntity<GeneralResponse> deletePaymentOfUser(@PathVariable Long paymentId){
		log.info("Deleting the payment of user");
		
		GeneralResponse generalResponse = paymentInformationService.deletePaymentInformation(paymentId);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
}
