package bg.ittalents.efficientproject.model.pojo;

public class Element {
	private int tasks;
	private String name;
	
	public Element(int tasks, String name) {
		super();
		this.tasks = tasks;
		this.name = name;
	}

	public int getTasks() {
		return tasks;
	}

	public void setTasks(int tasks) {
		this.tasks = tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
