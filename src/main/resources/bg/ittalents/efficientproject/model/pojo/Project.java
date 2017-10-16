package bg.ittalents.efficientproject.model.pojo;

import java.sql.Date;

public class Project {
	private int id;
	private String name;
	private Date deadline;

	public Project(String name, Date deadline) {
		this.name = name;
		this.deadline = deadline;
	}

	public Project(int id, String name, Date deadline) {
		this(name, deadline);
		this.id = id;
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
