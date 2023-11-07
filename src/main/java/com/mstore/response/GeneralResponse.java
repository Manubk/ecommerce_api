package com.mstore.response;

import lombok.Data;

@Data
public class GeneralResponse {

	private boolean isSuccess;
	private String message;
	
	private GeneralResponse(GeneralResposeBuilder generalResposeBuilder) {
		this.isSuccess = generalResposeBuilder.isSuccess;
		this.message = generalResposeBuilder.message;
	}
	
	public static class GeneralResposeBuilder {
		
		private boolean isSuccess;	
		private String message;
		
		public GeneralResposeBuilder setIsSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
			return this;
		}
		
		public GeneralResposeBuilder setMessage(String message) {
			this.message = message;
			return this;
		}
		
		public GeneralResponse build() {
			return new GeneralResponse(this);
		}
	}
	
}
