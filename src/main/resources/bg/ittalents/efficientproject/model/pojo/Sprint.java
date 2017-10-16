package bg.ittalents.efficientproject.model.pojo;

import java.sql.Date;
import java.util.Calendar;

public class Sprint {
	private int id;
	private String name;
	private Date startDate;
	private int duration;

	public Sprint(String name, int duration) {
		this.name = name;
		this.duration = duration;
		this.startDate = new Date(Calendar.getInstance().getTime().getTime());// TODO ????
	}

	public Sprint(int id, String name, Date startDate, int duration) {
		this(name, duration);
		this.id = id;
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

}
