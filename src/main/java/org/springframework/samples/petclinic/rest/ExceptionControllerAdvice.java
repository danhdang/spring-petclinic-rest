/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exception.RequestDataValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Vitaliy Fedoriv
 *
 */

@ControllerAdvice
public class ExceptionControllerAdvice 
{

	private static Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@ExceptionHandler(RequestDataValidationException.class)
	public ResponseEntity<ErrorResponse> handleValidationFailed(RequestDataValidationException ex) 
	{
		logger.debug(" Sending Error Response");

		ErrorResponse errorResponse = new ErrorResponse(ex.getTraceId(),
				ex.getErrorCode(),
				ex.getErrorMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
	}

	private class ErrorResponse 
	{
	    public final String traceId;
		public final String errorCode;
	    public final String errorMessage;
	    
		public String getTraceId() {
			return traceId;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		@Override
		public String toString() {
			return "ErrorResponse [traceId=" + traceId + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage
					+ "]";
		}
		public ErrorResponse(String traceId, String errorCode, String errorMessage) {
			super();
			this.traceId = traceId;
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception(Exception e) {
		ObjectMapper mapper = new ObjectMapper();
		ErrorInfo errorInfo = new ErrorInfo(e);
		String respJSONstring = "{}";
		try {
			respJSONstring = mapper.writeValueAsString(errorInfo);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return ResponseEntity.badRequest().body(respJSONstring);
	}

	private class ErrorInfo {
		public final String className;
		public final String exMessage;

		public ErrorInfo(Exception ex) {
			this.className = ex.getClass().getName();
			this.exMessage = ex.getLocalizedMessage();
		}
	}
}
