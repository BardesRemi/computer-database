package com.excilys.Mars2020.cdb.model;

import java.time.LocalDate;

/**
 * Computer representation
 * @author remi
 *
 */
public class Computer {
	
	//required attribute
	private String name;
	//optionnal attributes
	private int id;
	private LocalDate introduced;
	private LocalDate discontinued;
	private int companyId;
	
	/**
	 * Constructor using BUILDER design pattern
	 * @param name
	 */
	private Computer (Builder builder) {
		this.name = builder.name;
		this.id = builder.id;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
	}

	@Override
	public String toString() {
		return "Computer [name=" + name + ", id=" + id + ", introduce=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + companyId + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getIntroduce() {
		return introduced;
	}

	public void setIntroduce(LocalDate introduce) {
		this.introduced = introduce;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public int getCompany_id() {
		return companyId;
	}

	public void setCompany_id(int company_id) {
		this.companyId = company_id;
	}
	
	/**
	 * Builder design pattern
	 * Used for a modulable Computer constructor
	 * @author remi
	 *
	 */
	public static class Builder{

		private final String name;
		private int id;
		private LocalDate introduced;
		private LocalDate discontinued;
		private int companyId;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
		public Builder introduced(LocalDate date) {
			this.introduced = date;
			return this;
		}
		
		public Builder discontinued(LocalDate date) {
			this.discontinued = date;
			return this;
		}
		
		public Builder companyId(int id) {
			this.companyId = id;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}
	
}
