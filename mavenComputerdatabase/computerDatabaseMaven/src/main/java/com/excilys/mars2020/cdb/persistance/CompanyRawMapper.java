package com.excilys.mars2020.cdb.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.mars2020.cdb.model.Company;

@Component
public class CompanyRawMapper implements RowMapper<Company> {
	
	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Company.Builder().name(rs.getString("name")).compId(rs.getInt("id")).build();
	}
	
}
