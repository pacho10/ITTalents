package bg.ittalents.efficientproject.model.pojo;

import java.sql.Date;
import java.util.Calendar;

public class Sprint {
	private int id;
	private String name;
	private Date startDate;
	private int duration;
	private int project_id;

	public Sprint(String name, Date startDate, int duration,int project_id) {
		this.name = name;
		this.duration = duration;
		this.setProject_id(project_id);
		this.startDate = new Date(Calendar.getInstance().getTime().getTime());// TODO ????
	}

	public Sprint(int id, String name, Date startDate, int duration,int project_id) {
		this(name, startDate, duration, project_id);
		this.id = id;
		this.startDate=startDate;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

}
