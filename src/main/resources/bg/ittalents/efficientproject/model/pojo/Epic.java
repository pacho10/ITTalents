package bg.ittalents.efficientproject.model.pojo;

public class Epic {
	private int id;
	private String name;
	private int estimate;// estimate time in days
	private String descripion;
	private Project project;
	
	

	public Epic(String name, int estimate, String descripion, Project project) {
		super();
		this.name = name;
		this.estimate = estimate;
		this.descripion = descripion;
		this.project = project;
	}

	public Epic(int id, String name, int estimate, String descripion, Project project) {
		this(name, estimate, descripion, project);
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

	public String getDescripion() {
		return descripion;
	}

	public void setDescripion(String descripion) {
		this.descripion = descripion;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
