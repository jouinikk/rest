package net.jouini.ws;

public class Category {

	private int id;
	private String label;	
	
	public Category() {}
	public Category(int id,String label) {
		this.id=id;
		this.label= label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", label=" + label + "]";
	}
	
	
}
