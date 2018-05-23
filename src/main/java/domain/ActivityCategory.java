
package domain;

public enum ActivityCategory {

	ENTERTAINMENT("ENTERTAINMENT", 1), SPORT("SPORT", 2), TOURISM("TOURISM", 3);

	private String	name;
	private int		id;


	private ActivityCategory(final String name, final int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getId() {
		return this.id;

	}
}
