package com.excilys.mars2020.cdb.dto;

public class UserDTO {

	private String id;
	private String username;
	private String password;
	private String enabled;
	private String role;
	
	public UserDTO(Builder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
		this.enabled = builder.enabled;
		this.role = builder.role;
	}
	
	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEnabled() {
		return enabled;
	}

	public String getRole() {
		return role;
	}
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserDTO other = (UserDTO) obj;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public static class Builder{
		
		private String id;
		private final String username;
		private final String password;
		private String enabled;
		private String role;
		
		public Builder(String username, String password){
			this.username = username;
			this.password = password;
		}
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder role(String role) {
			this.role = role;
			return this;
		}
		
		public Builder enabled(String enabled) {
			this.enabled = enabled;
			return this;
		}
		
		public UserDTO build() {
			return new UserDTO(this);
		}
	}
}
