package com.excilys.mars2020.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Company representation
 * @author remi
 *
 */
@Entity
@Table(name="company")
public class Company {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long compId;
	@Column(name="name")
	private String name;
	
	public Company() {
		super();
	}
	
	public Company(Builder builder) {
		this.name = builder.name;
		this.compId = builder.compId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (compId ^ (compId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Company other = (Company) obj;
		if (compId != other.getCompId())
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public long getCompId() {
		return compId;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Company [id=" + compId + ", name=" + name + "]";
	}
	
	public static class Builder{
		private long compId;
		private String name;
		
		public Builder() { }
		
		public Builder compId(long compId) {
			this.compId = compId;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			return new Company(this);
		}
	}
	
}
