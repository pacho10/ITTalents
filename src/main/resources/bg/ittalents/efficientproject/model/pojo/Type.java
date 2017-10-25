package bg.ittalents.efficientproject.model.pojo;

public class Type {
	private int id;
	private String name;

	public Type(String name) {
		this.name = name;
	}

	public Type(int id, String name) {
		this(name);
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
}
