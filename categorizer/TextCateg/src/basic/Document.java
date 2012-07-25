package basic;

public class Document {

	private String id;
	private String category;
	private String text;

	public Document(String category, String text) {
		setCategory(category);
		setText(text);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return id + "\t" +category + "\t" + text;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
