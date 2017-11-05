package bg.ittalents.efficientproject.model.pojo;

import java.sql.Timestamp;

public class Task {

	private int id;
	private Type type;// types:bug,task,...
	private String summary;
	private String description;
	private float estimate;// estimate time in days
	private Timestamp creationDate;
	private Timestamp addetToSprintDate;
	private Timestamp assignedDate;
	private Timestamp finishedDate;
	private Timestamp stoppedDate;
	private Sprint sprint;
	private User reporter;
	private User assignee;
	private Epic epic;
	private TaskState status;
	private Timestamp updatedDate;

	public enum TaskState {
		OPEN, RESOLVED, CLOSED, INPROGRESS
	}

	public Task(Type type, String summary, String description, float estimate, User reporter, Epic epic) {
		this.status = TaskState.OPEN;
		this.type = type;
		this.summary = summary;
		this.description = description;
		this.estimate = estimate;
		this.reporter = reporter;
		this.epic = epic;
		this.creationDate = new Timestamp(System.currentTimeMillis());
		this.updatedDate = this.creationDate;
	}

	public Task(int id, Type type, String summary, String description, float estimate, Timestamp creationDate,
			Timestamp addetToSprintDate, Timestamp assignedDate, Timestamp finishedDate, Timestamp stoppedDate,
			Sprint sprint, User reporter, User assignee, Epic epic) {
		this(type, summary, description, estimate, reporter, epic);
		this.id = id;
		this.creationDate = creationDate;
		this.addetToSprintDate = addetToSprintDate;
		this.assignedDate = assignedDate;
		this.finishedDate = finishedDate;
		this.stoppedDate = stoppedDate;
		this.sprint = sprint;
		this.assignee = assignee;
		this.setStatus();
		this.setUpdatedDate();
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descripion) {
		this.description = descripion;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getStoppedDate() {
		return stoppedDate;
	}

	public void setStoppedDate(Timestamp stoppedDate) {
		this.stoppedDate = stoppedDate;
	}

	public Epic getEpic() {
		return epic;
	}

	public void setEpic(Epic epic) {
		this.epic = epic;
	}

	public TaskState getStatus() {
		return status;
	}

	private void setStatus() {
		// Timestamp creationDate = task.getCreationDate();
		// Timestamp addetToSprintDate = task.getAddetToSprintDate();
		Timestamp assignedDate = this.getAssignedDate();
		Timestamp finishedDate = this.getFinishedDate();
		// Timestamp stoppedDate = task.getStoppedDate();
		if (finishedDate != null) {
			this.status = TaskState.RESOLVED;
			return;
		} else {
			if (assignedDate != null) {
				this.status = TaskState.INPROGRESS;
				return;
			}
		}
		this.status = TaskState.OPEN;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	private void setUpdatedDate() {
		Timestamp creationDate = this.getCreationDate();
		Timestamp addetToSprintDate = this.getAddetToSprintDate();
		Timestamp assignedDate = this.getAssignedDate();
		Timestamp finishedDate = this.getFinishedDate();
		// Timestamp stoppedDate = task.getStoppedDate();
		if (finishedDate != null) {
			this.updatedDate = finishedDate;
			return;
		} else {
			if (assignedDate != null) {
				this.updatedDate = assignedDate;
				return;
			} else {
				if (addetToSprintDate != null) {
					this.updatedDate = addetToSprintDate;
					return;
				}
			}
		}
		this.updatedDate = creationDate;
	}

}
