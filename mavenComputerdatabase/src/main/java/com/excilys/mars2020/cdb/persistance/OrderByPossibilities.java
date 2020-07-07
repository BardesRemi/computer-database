package com.excilys.mars2020.cdb.persistance;

public enum OrderByPossibilities {
	ID_UP(" ORDER BY pc.id "),
	COMPANY_UP(" ORDER BY comp.name "),
	PC_UP(" ORDER BY pc.name ");
	
	private final String OrderBy;
	
	private OrderByPossibilities (String str) {
		this.OrderBy = str + "LIMIT :start, :qty";
	}
	
	public String getOrderBy() {
		return this.OrderBy;
	}
}
