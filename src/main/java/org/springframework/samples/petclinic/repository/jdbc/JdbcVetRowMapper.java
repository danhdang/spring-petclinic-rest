package org.springframework.samples.petclinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * {@link RowMapper} implementation mapping data from a {@link ResultSet} to the corresponding properties
 * of the {@link JdbcVet} class.
 */
public class JdbcVetRowMapper implements RowMapper<JdbcVet> {

    @Override
    public JdbcVet mapRow(ResultSet rs, int rownum) throws SQLException {
        JdbcVet vet = new JdbcVet();
        vet.setId(rs.getInt("vets_id"));
        vet.setFirstName(rs.getString("first_name"));
        vet.setLastName(rs.getString("last_name"));
        return vet;
    }
}
