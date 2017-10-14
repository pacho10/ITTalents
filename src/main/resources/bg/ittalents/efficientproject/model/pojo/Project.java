package bg.ittalents.efficientproject.model.pojo;

import java.sql.Timestamp;

public class Project {
	private int id;
	private String name;
	private Timestamp deadline;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
