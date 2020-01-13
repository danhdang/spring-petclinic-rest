package org.springframework.samples.petclinic.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PetDto 
{
    @JsonIgnore
	private Integer id;
	
    private String name;
	private String birthDate;
    private String type;
    
    private List<VisitDto> myVisits = new ArrayList<>();
    private OwnerDto owner;
    
    public PetDto(Integer id, String name, String birthDate, String type) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.type = type;
	}
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<VisitDto> getMyVisits() {
		return myVisits;
	}
	public void setMyVisits(List<VisitDto> myVisits) {
		this.myVisits = myVisits;
	}
	public OwnerDto getOwner() {
		return owner;
	}
	public void setOwner(OwnerDto owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PetDto other = (PetDto) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}
