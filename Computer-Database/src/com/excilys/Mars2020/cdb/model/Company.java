package com.excilys.Mars2020.cdb.model;

/**
 * Company representation
 * @author remi
 *
 */
public class Company {
	
	private int id;
	private String name;
	
	public Company(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
}
