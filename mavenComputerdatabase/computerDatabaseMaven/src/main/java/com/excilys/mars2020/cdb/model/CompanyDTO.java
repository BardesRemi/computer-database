package com.excilys.mars2020.cdb.model;

public class CompanyDTO {
	
	private String compId;
	private String name;
	
	public CompanyDTO(Builder builder) {
		this.name = builder.name;
		this.compId = builder.compId;
	}

	public String getCompId() {
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
		private String compId;
		private String name;
		
		public Builder() { }
		
		public Builder compId(String compId) {
			this.compId = compId;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public CompanyDTO build() {
			return new CompanyDTO(this);
		}
	}
}
