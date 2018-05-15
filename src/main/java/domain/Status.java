
package domain;

public enum Status {

	PENDING("PENDING", 1), REJECTED("REJECTED", 2), DUE("DUE", 3), ACCEPTED("ACCEPTED", 4), CANCELLED("CANCELLED", 5);

	private int		id;
	private String	name;


	private Status(final String name, final int id) {
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
