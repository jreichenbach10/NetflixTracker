public class Title {
	
	private String name;
	private String releaseDate;
	private String type;
	private String description;
	
	public Title(String name, String releaseDate, String type, String description) {
		this.name = name;
		this.releaseDate = releaseDate;
		this.type = type;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getReleaseDate() {
		return this.releaseDate;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	@Override public String toString() {
		return getName() + " is a " + getType() + " releasing on " + getReleaseDate();
	}
	
	
}
