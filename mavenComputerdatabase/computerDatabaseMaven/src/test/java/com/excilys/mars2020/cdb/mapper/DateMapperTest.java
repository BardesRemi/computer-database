package com.excilys.mars2020.cdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Test;

public class DateMapperTest {

	@Test
	public void timestamptoLocalDateTest() {
		Timestamp tmsp = Timestamp.valueOf("2020-01-01 00:00:00");
		LocalDate localD = LocalDate.of(2020, 01, 01);
		assertTrue(DateMapper.timestampToLocalDate(null).isEmpty());
		assertTrue(DateMapper.timestampToLocalDate(tmsp).isPresent());
		assertTrue(localD.isEqual(DateMapper.timestampToLocalDate(tmsp).get()));
	}
	
	@Test
	public void localDateToTimestampTest() {
		Timestamp tmsp = Timestamp.valueOf("2020-01-01 00:00:00");
		LocalDate localD = LocalDate.of(2020, 01, 01);
		assertTrue(DateMapper.localDateToTimestamp(null).isEmpty());
		assertTrue(DateMapper.localDateToTimestamp(localD).isPresent());
		assertEquals(tmsp, DateMapper.localDateToTimestamp(localD).get());
	}
	
	@Test
	public void stringToLocalDateTest() {
		String sDate = "01/01/2020";
		LocalDate localD = LocalDate.of(2020, 01, 01);
		assertTrue(DateMapper.stringToLocalDate(null).isEmpty());
		assertTrue(DateMapper.stringToLocalDate("").isEmpty());
		assertTrue(DateMapper.stringToLocalDate("2020-01-01").isEmpty());
		assertTrue(DateMapper.stringToLocalDate("2020/01/01").isEmpty());
		assertTrue(DateMapper.stringToLocalDate("nonsens date").isEmpty());
		assertEquals(DateMapper.stringToLocalDate(sDate).get(), localD);
	}
}
