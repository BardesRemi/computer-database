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
	private Company company;
	
	/**
	 * Constructor using BUILDER design pattern
	 * @param name
	 */
	private Computer (Builder builder) {
		this.name = builder.name;
		this.id = builder.id;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	@Override
	public String toString() {
		return "Computer [name=" + name + ", id=" + id + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
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

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getcompany() {
		return company;
	}

	public void setcompany(Company company) {
		this.company = company;
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
		private Company company;
		
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
		
		public Builder company(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}
	
}
