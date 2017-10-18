package bg.ittalents.efficientproject.model.pojo;

import java.sql.Timestamp;

public class Task {

	private int id;
	private String type;// types:bug,task,...
	private String summary;
	private String descripion;
	private float estimate;// estimate time in days
	private Timestamp creationDate;
	private Timestamp addetToSprintDate;
	private Timestamp assignedDate;
	private Timestamp finishedDate;
	private Timestamp stoppedDate;
	// statuses:open,todo,inprogress,resolved,closed //TODO enum? and how to handle it in db and here
	private Sprint sprint;
	private User reporter;
	private User assignee;
	private Epic epic;

	public Task(Type type, String summary, String descripion, float estimate, User reporter, Epic epic) {
		this.type = type;
		this.summary = summary;
		this.descripion = descripion;
		this.estimate = estimate;
		this.reporter = reporter;
		this.epic = epic;
		this.creationDate = new Timestamp(System.currentTimeMillis());//TODO tuj tyj li e????
	}

	public Task(int id, Type type, String summary, String descripion, float estimate, Timestamp creationDate,
			Timestamp addetToSprintDate, Timestamp assignedDate, Timestamp finishedDate, Timestamp stoppedDate
			, Sprint sprint, User reporter, User assignee, Epic epic) {
		this(type, summary, descripion, estimate, reporter, epic);
		this.id = id;
		this.creationDate = creationDate;
		this.addetToSprintDate = addetToSprintDate;
		this.assignedDate = assignedDate;
		this.finishedDate = finishedDate;
		this.stoppedDate = stoppedDate;
		this.sprint = sprint;
		this.assignee = assignee;
	}

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

}
