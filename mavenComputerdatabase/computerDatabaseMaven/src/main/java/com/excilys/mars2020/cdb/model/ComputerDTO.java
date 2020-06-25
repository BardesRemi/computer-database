package com.excilys.mars2020.cdb.model;


/**
 * DTO class for Computer Objects
 * @author remi
 *
 */
public class ComputerDTO {
		//required attribute
		private String name;
		//optionnal attributes
		private String pcId;
		private String introduced;
		private String discontinued;
		private CompanyDTO company;
		
		/**
		 * Constructor using BUILDER design pattern
		 * @param name
		 */
		private ComputerDTO (Builder builder) {
			this.name = builder.name;
			this.pcId = builder.pcId;
			this.introduced = builder.introduced;
			this.discontinued = builder.discontinued;
			this.company = builder.company;
		}
		
		@Override
		public String toString() {
			return "ComputerDTO [name=" + name + ", pcId=" + pcId + ", introduced=" + introduced + ", discontinued="
					+ discontinued + ", "+ company + "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ComputerDTO other = (ComputerDTO) obj;
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

		public String getPcId() {
			return pcId;
		}

		public String getIntroduced() {
			return introduced;
		}

		public String getDiscontinued() {
			return discontinued;
		}

		public CompanyDTO getCompany() {
			return company;
		}

		/**
		 * Builder design pattern
		 * Used for a modulable ComputerDTO constructor
		 * @author remi
		 *
		 */
		public static class Builder{

			private final String name;
			private String pcId;
			private String introduced;
			private String discontinued;
			private CompanyDTO company;
			
			public Builder(String name) {
				this.name = name;
			}
			
			public Builder pcId(String pcId) {
				this.pcId = pcId;
				return this;
			}
			
			public Builder introduced(String date) {
				this.introduced = date;
				return this;
			}
			
			public Builder discontinued(String date) {
				this.discontinued = date;
				return this;
			}
			
			public Builder company(CompanyDTO company) {
				this.company = company;
				return this;
			}
			
			public ComputerDTO build() {
				return new ComputerDTO(this);
			}
		}
		
}
