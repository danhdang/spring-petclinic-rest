/*
 * Copyright 2002-2015 the original author or authors.
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
package org.springframework.samples.petclinic.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.util.EntityUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link RowMapper} implementation mapping data from a {@link ResultSet} to the corresponding properties
 * of the {@link Visit} class. With extended functionality to include owner, pet and vet.
 */
class JdbcVisitRowMapperExt implements RowMapper<Visit> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcVisitRowMapperExt(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Visit visit = new Visit();
        JdbcPet pet;
        PetType petType;
        Owner owner;
        Vet vet;
        List<Specialty> specialties;
        List<Integer> vetSpecialtiesIds;
        visit.setId(rs.getInt("visit_id"));
        Date visitDate = rs.getDate("visit_date");
        visit.setDate(new Date(visitDate.getTime()));
        visit.setDescription(rs.getString("description"));
        Map<String, Object> params = new HashMap<>();
        params.put("id", rs.getInt("pets_id"));
        pet = this.namedParameterJdbcTemplate.queryForObject(
            "SELECT pets.id as pets_id, name, birth_date, type_id, owner_id FROM pets WHERE pets.id=:id",
            params,
            new JdbcPetRowMapper());
        params.put("type_id", pet.getTypeId());
        petType = this.namedParameterJdbcTemplate.queryForObject(
            "SELECT id, name FROM types WHERE id= :type_id",
            params,
            BeanPropertyRowMapper.newInstance(PetType.class));
        pet.setType(petType);
        params.put("owner_id", pet.getOwnerId());
        owner = this.namedParameterJdbcTemplate.queryForObject(
            "SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE id= :owner_id",
            params,
            BeanPropertyRowMapper.newInstance(Owner.class));
        pet.setOwner(owner);
        visit.setPet(pet);
        params.put("vet_id", rs.getInt("vets_id"));
        vet = this.namedParameterJdbcTemplate.queryForObject(
            "SELECT id, first_name, last_name FROM vets WHERE id= :vet_id",
            params,
            BeanPropertyRowMapper.newInstance(Vet.class));
        specialties = this.namedParameterJdbcTemplate.query(
            "SELECT id, name FROM specialties", params, BeanPropertyRowMapper.newInstance(Specialty.class));
        vetSpecialtiesIds = this.namedParameterJdbcTemplate.query(
            "SELECT specialty_id FROM vet_specialties WHERE vet_id=:vet_id",
            params,
            new BeanPropertyRowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int row) throws SQLException {
                    return rs.getInt(1);
                }
            });
        for (int specialtyId : vetSpecialtiesIds) {
            Specialty specialty = EntityUtils.getById(specialties, Specialty.class, specialtyId);
            vet.addSpecialty(specialty);
        }
        visit.setVet(vet);
        return visit;
    }
}
