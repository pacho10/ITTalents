package bg.ittalents.efficientproject.model.pojo;

import java.sql.Timestamp;

public class Issue {
	private int id;
	private String summary;
	private String descripion;
	private float estimate;// estimate time in days
	private Timestamp creationDate;
	private Timestamp addetToSprintDate;
	private Sprint sprint;
	private Project project;
	private User assignee;
	private User reporter;
	private String type;// types:
	private String status;// statuses:started,todo,inprogress,finished,stopped
	private int epicId; // ???
	private Timestamp assignedDate;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescripion() {
		return descripion;
	}

	public void setDescripion(String descripion) {
		this.descripion = descripion;
	}

	public float getEstimate() {
		return estimate;
	}

	public void setEstimate(float estimate) {
		this.estimate = estimate;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getAddetToSprintDate() {
		return addetToSprintDate;
	}

	public void setAddetToSprintDate(Timestamp addetToSprintDate) {
		this.addetToSprintDate = addetToSprintDate;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public User getReporter() {
		return reporter;
	}

	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getEpicId() {
		return epicId;
	}

	public void setEpicId(int epicId) {
		this.epicId = epicId;
	}

	public Timestamp getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Timestamp assignedDate) {
		this.assignedDate = assignedDate;
	}

	public Timestamp getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(Timestamp finishedDate) {
		this.finishedDate = finishedDate;
	}

	private Timestamp finishedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
