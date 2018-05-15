
package domain;

public enum ActivityCategory {

	ENTERTAINMENT("ENTERTAINMENT"), SPORT("SPORT"), TOURISM("TOURISM");

	private String	id;


	private ActivityCategory(final String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;

	}
}
