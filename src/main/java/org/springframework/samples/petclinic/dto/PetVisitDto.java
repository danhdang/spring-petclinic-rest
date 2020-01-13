package org.springframework.samples.petclinic.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PetVisitDto extends PetDto
{
    private List<VisitDto> myVisits = new ArrayList<>();
	
	public PetVisitDto(Integer id, String name, String birthDate, String type) {
		super(id, name, birthDate, type);
	}

	public List<VisitDto> getMyVisits() {
		return myVisits;
	}

	public void setMyVisits(List<VisitDto> myVisits) {
		this.myVisits = myVisits;
	}
   
}
