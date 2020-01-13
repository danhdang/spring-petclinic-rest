package org.springframework.samples.petclinic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.samples.petclinic.dto.OwnerDto;
import org.springframework.samples.petclinic.dto.PetDto;
import org.springframework.samples.petclinic.dto.PetVisitDto;
import org.springframework.samples.petclinic.dto.VisitDto;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;

public class PetClinicUtil 
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Utility Method to convert Pet Entity to a DTO object
	 * @param myPets
	 * @param mapPetTypesCache
	 * @return
	 */
	public static List<PetDto> convertToDto(List<Pet> pets) 
	{
		List<PetDto> result = null;

		if(pets != null && ! pets.isEmpty()) 
		{
			result = new ArrayList<>();

			for(Pet p : pets) 
			{
				String strDOB = dateToString(p.getBirthDate());

				PetDto dto = new PetDto(p.getId(),
						p.getName(),
						strDOB,
						p.getType().getName());

				result.add(dto);
			}
		}
		return result;
	}

	/**
	 * Utility Method to collect list of all Pets visited to a Vet
	 * @param vet
	 * @return
	 */
	public static List<PetVisitDto> convertToDto(Vet vet) 
	{
		List<PetVisitDto> result = null;

		if(vet != null && ! vet.getVisits().isEmpty()) 
		{
			result = new ArrayList<>();
			Map<Integer,PetVisitDto> map = new HashMap<>();


			Set<Visit> visits = vet.getVisits();

			for(Visit v : visits) 
			{
				Pet p = v.getPet();

				/*
				 * Pet details already captured then modify the visit list
				 */
				PetVisitDto petVisitDto = map.get(p.getId());

				if(petVisitDto != null)
				{
					//Visit details
					VisitDto visitDto = new VisitDto(v.getDate().toString(), v.getDescription()); 
					petVisitDto.getMyVisits().add(visitDto);
				}
				else {

					String strDOB = dateToString(p.getBirthDate());

					//Pet Details
					petVisitDto = new PetVisitDto(p.getId(),
							p.getName(),
							strDOB,
							p.getType().getName());


					//Owner Details
					Owner owner = p.getOwner();
					OwnerDto ownerDto = new OwnerDto(owner.getFirstName(),
							owner.getLastName(),
							owner.getTelephone());
					petVisitDto.setOwner(ownerDto);

					//Visit details
					VisitDto visitDto = new VisitDto(v.getDate().toString(), v.getDescription()); 
					petVisitDto.getMyVisits().add(visitDto);

					map.put(p.getId(), petVisitDto);
				}
			}

			result = map.values().stream().collect(Collectors.toList());
		}
		return result;
	}

	public static String dateToString(Date dt) {
		return sdf.format(dt);
	}

	public static Date stringToDate(String strDt) {
		try {
			return sdf.parse(strDt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
