package com.excilys.Mars2020.cdb.model;

/**
 * Company representation
 * @author remi
 *
 */
public class Company {
	
	private long compId;
	private String name;
	
	public Company(Builder builder) {
		this.name = builder.name;
		this.compId = builder.compId;
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
