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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyDTO other = (CompanyDTO) obj;
		if (!compId.equals(other.compId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
