package bg.ittalents.efficientproject.model.pojo;

import java.sql.Date;
import java.time.LocalDate;

public class Project {
	private int id;
	private String name;
	private Date deadline;
	private Organization organization;
	private LocalDate startDate;

	public Project(String name, Date deadline, Organization organization) {
		this.name = name;
		this.deadline = deadline;
		this.organization = organization;
		this.setStartDate(LocalDate.now());
	}

	public Project(int id, String name, Date deadline, Organization organization,LocalDate startDate) {
		this(name, deadline, organization);
		this.id = id;
		this.setStartDate(startDate);
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
}
