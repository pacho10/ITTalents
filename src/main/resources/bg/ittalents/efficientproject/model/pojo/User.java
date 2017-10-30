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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	


}
