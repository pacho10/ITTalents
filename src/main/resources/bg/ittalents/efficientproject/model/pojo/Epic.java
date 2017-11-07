package bg.ittalents.efficientproject.model.pojo;

public class Epic {
	private int id;
	private String name;
	private int estimate;// estimate time in days
	private String description;
	private Project project;
	
	

	public Epic(String name, int estimate, String descripion, Project project) {
		super();
		this.name = name;
		this.estimate = estimate;
		this.description = descripion;
		this.project = project;
	}

	public Epic(int id, String name, int estimate, String description, Project project) {
		this(name, estimate, description, project);
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

	public int getEstimate() {
		return estimate;
	}

	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "Epic [id=" + id + ", name=" + name + ", estimate=" + estimate + ", description=" + description
				+ ", project=" + project + "]";
	}
}
