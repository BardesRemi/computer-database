package com.excilys.mars2020.cdb.model;

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
	private long pcId;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	/**
	 * Constructor using BUILDER design pattern
	 * @param name
	 */
	private Computer (Builder builder) {
		this.name = builder.name;
		this.pcId = builder.pcId;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	@Override
	public String toString() {
		return "Computer [name=" + name + ", id=" + pcId + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}

	public String getName() {
		return name;
	}

	public long getPcId() {
		return pcId;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public Company getcompany() {
		return company;
	}
	
	/**
	 * Builder design pattern
	 * Used for a modulable Computer constructor
	 * @author remi
	 *
	 */
	public static class Builder{

		private final String name;
		private int pcId;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder pcId(int pcId) {
			this.pcId = pcId;
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
