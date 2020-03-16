package com.excilys.mars2020.cdb.model;

import java.time.LocalDate;

import com.excilys.mars2020.cdb.model.Computer.Builder;

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
		private String companyId;
		private String companyName;
		
		/**
		 * Constructor using BUILDER design pattern
		 * @param name
		 */
		private ComputerDTO (Builder builder) {
			this.name = builder.name;
			this.pcId = builder.pcId;
			this.introduced = builder.introduced;
			this.discontinued = builder.discontinued;
			this.companyId = builder.companyId;
			this.companyName = builder.companyName;
		}
		
		@Override
		public String toString() {
			return "ComputerDTO [name=" + name + ", pcId=" + pcId + ", introduced=" + introduced + ", discontinued="
					+ discontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
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

		public String getCompanyId() {
			return companyId;
		}

		public String getCompanyName() {
			return companyName;
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
			private String companyId;
			private String companyName;
			
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
			
			public Builder companyId(String companyId) {
				this.companyId = companyId;
				return this;
			}
			
			public Builder companyName(String companyName) {
				this.companyName = companyName;
				return this;
			}
			
			public ComputerDTO build() {
				return new ComputerDTO(this);
			}
		}
}
