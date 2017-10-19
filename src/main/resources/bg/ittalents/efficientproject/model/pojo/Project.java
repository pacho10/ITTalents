package bg.ittalents.efficientproject.model.pojo;

import java.sql.Date;

public class Project {
	private int id;
	private String name;
	private Date deadline;
	private Organization organization;

	public Project(String name, Date deadline, Organization organization) {
		this.name = name;
		this.deadline = deadline;
		this.organization = organization;
	}

	public Project(int id, String name, Date deadline, Organization organization) {
		this(name, deadline, organization);
		this.id = id;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
