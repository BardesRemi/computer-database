package com.excilys.mars2020.cdb.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.mars2020.cdb.mapper.DateMapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;

@Component
public class ComputerRawMapper implements RowMapper<Computer>{

	@Autowired
	private DateMapper dateMapper;
	
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Optional<LocalDate> introD = dateMapper.timestampToLocalDate(rs.getTimestamp("pc.introduced"));
		Optional<LocalDate> discontD = dateMapper.timestampToLocalDate(rs.getTimestamp("pc.discontinued"));
		Computer pc = new Computer.Builder(rs.getString("pc.name"))
				.pcId(rs.getLong("pc.id"))
				.introduced(introD.isEmpty() ? null : introD.get())
				.discontinued(discontD.isEmpty() ? null : discontD.get())
				.company(new Company.Builder().name(rs.getString("comp.name")).compId(rs.getInt("pc.company_id")).build()).build();
		return pc;
	}

}
