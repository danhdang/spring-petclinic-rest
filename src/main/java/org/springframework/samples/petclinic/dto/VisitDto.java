package org.springframework.samples.petclinic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class VisitDto 
{
	private String date;
	private String reason;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public VisitDto(String date, String reason) {
		super();
		this.date = date;
		this.reason = reason;
	}


}
