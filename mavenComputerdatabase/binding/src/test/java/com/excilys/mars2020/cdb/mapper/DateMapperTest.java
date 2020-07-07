package com.excilys.mars2020.cdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Test;

public class DateMapperTest {

	private DateMapper dateMapper = new DateMapper();

	@Test
	public void timestamptoLocalDateTest() {
		Timestamp tmsp = Timestamp.valueOf("2020-01-01 00:00:00");
		LocalDate localD = LocalDate.of(2020, 01, 01);
		assertTrue(dateMapper.timestampToLocalDate(null).isEmpty());
		assertTrue(dateMapper.timestampToLocalDate(tmsp).isPresent());
		assertTrue(localD.isEqual(dateMapper.timestampToLocalDate(tmsp).get()));
	}
	
	@Test
	public void localDateToTimestampTest() {
		Timestamp tmsp = Timestamp.valueOf("2020-01-01 00:00:00");
		LocalDate localD = LocalDate.of(2020, 01, 01);
		assertTrue(dateMapper.localDateToTimestamp(null).isEmpty());
		assertTrue(dateMapper.localDateToTimestamp(localD).isPresent());
		assertEquals(tmsp, dateMapper.localDateToTimestamp(localD).get());
	}
	
	@Test
	public void stringToLocalDateTest() {
		LocalDate localD = LocalDate.of(2020, 01, 01);
		LocalDate localD2 = LocalDate.of(2020, 06, 25);
		assertTrue(dateMapper.stringToLocalDate(null).isEmpty());
		assertTrue(dateMapper.stringToLocalDate("").isEmpty());
		assertTrue(dateMapper.stringToLocalDate("2020/01/01").isEmpty());
		assertTrue(dateMapper.stringToLocalDate("nonsens date").isEmpty());
		assertEquals(dateMapper.stringToLocalDate("01/01/2020").get(), localD);
		assertEquals(dateMapper.stringToLocalDate("2020-01-01").get(), localD);
		assertEquals(dateMapper.stringToLocalDate("25/06/2020").get(), localD2);
		assertEquals(dateMapper.stringToLocalDate("2020-06-25").get(), localD2);
	}
}
