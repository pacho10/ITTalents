package bg.ittalents.efficientproject.model.pojo;

public class User {
	private int id;
	private String fullName;
	private String email;
	private String password;
	private String avatarPath;
	private boolean admin;
	private Organization organization;

	public User(int id, String fullName, String email, String password, String avatarPath, boolean admin,
			Organization organization) {
		this(fullName, email, password, admin);
		this.id = id;
		this.avatarPath = avatarPath;
		this.organization = organization;
	}

	public User(String fullName, String email, String password, boolean admin) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}

	// execute query:
	// alter table users change companies_id organization_id int(11);
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

}
