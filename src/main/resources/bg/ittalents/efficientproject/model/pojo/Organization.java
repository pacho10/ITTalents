package bg.ittalents.efficientproject.model.pojo;

public class Organization {
	private int id;
	private String name;
	private User admin;

	public Organization(int id, String name, User admin) {
		this.id = id;
		this.name = name;
		this.admin = admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
