package bg.ittalents.efficientproject.model.pojo;


import java.io.File;

import bg.ittalents.efficientproject.model.dao.INFO;

public class User {
	private static final String DEFAUL_AVATAR_PATH = INFO.IMAGES_PATH + File.separator+"avatar-default.jpg";
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String avatarPath;
	private boolean admin;
	private Organization organization;
	private boolean isEmployed=false;

	public User(int id, String firstName, String lastName, String email, String password, String avatarPath, boolean admin,
			Organization organization,boolean isEmployed) {
		this(firstName,lastName, email, password, admin,organization);
		this.id = id;
		this.avatarPath = avatarPath;
		this.isEmployed=isEmployed;
	}

	public User(String firstName, String lastName, String email, String password, boolean admin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.avatarPath=DEFAUL_AVATAR_PATH;
	}
	
	public User(String firstName, String lastName, String email, String password, boolean admin,Organization organization) {
		this(firstName, lastName, email, password, admin);
		this.organization=organization;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEmployed() {
		return isEmployed;
	}

	public void setEmployed(boolean isEmployed) {
		this.isEmployed = isEmployed;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", avatarPath=" + avatarPath + ", admin=" + admin + ", organization="
				+ organization + ", isEmployed=" + isEmployed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((avatarPath == null) ? 0 : avatarPath.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + (isEmployed ? 1231 : 1237);
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		if (admin != other.admin)
			return false;
		if (avatarPath == null) {
			if (other.avatarPath != null)
				return false;
		} else if (!avatarPath.equals(other.avatarPath))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (isEmployed != other.isEmployed)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
