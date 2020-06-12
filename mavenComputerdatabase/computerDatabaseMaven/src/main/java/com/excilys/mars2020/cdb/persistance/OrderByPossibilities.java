package com.excilys.mars2020.cdb.persistance;

public enum OrderByPossibilities {
	ID_UP(" ORDER BY pc.id LIMIT ?, ?"),
	COMPANY_UP(" ORDER BY comp.name LIMIT ?, ?"),
	PC_UP(" ORDER BY pc.name LIMIT ?, ?");
	
	private final String OrderBy;
	
	private OrderByPossibilities (String str) {
		this.OrderBy = str;
	}
	
	public String getOrderBy() {
		return this.OrderBy;
	}
}
