package test.app;

public class Exercise {
	private int id;
	private String name;
	private String category;
	private String bodyPart;
	
	public Exercise(int id, String name, String bodyPart, String category) {
		this.id = id;
		this.name = name;
		this.bodyPart = bodyPart;
		this.category = category;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}
}
