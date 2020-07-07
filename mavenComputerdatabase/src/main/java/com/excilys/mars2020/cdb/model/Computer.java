package com.excilys.mars2020.cdb.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Computer representation
 * @author remi
 *
 */
@Entity
@Table(name="computer")
public class Computer {
	
	//required attribute
	@Column(name="name")
	private String name;
	//optionnal attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long pcId;
	@Column(name="introduced")
	private LocalDate introduced;
	@Column(name="discontinued")
	private LocalDate discontinued;
	@JoinColumn(name="company_id")
	@ManyToOne
	private Company company;
	
	public Computer () {
		super();
	}
	
	/**
	 * Constructor using BUILDER design pattern
	 * @param name
	 */
	private Computer (Builder builder) {
		this.name = builder.name;
		if(builder.pcId > 0) {
			this.pcId = builder.pcId;
		}
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	@Override
	public String toString() {
		return "Computer [name=" + name + ", id=" + pcId + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pcId == null) ? 0 : pcId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pcId == null) {
			if (other.pcId != null)
				return false;
		} else if (!pcId.equals(other.pcId))
			return false;
		return true;
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
		private long pcId;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder pcId(Long l) {
			this.pcId = l;
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
