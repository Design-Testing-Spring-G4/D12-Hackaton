
package domain;

public enum Priority {

	HIGH("HIGH"), NEUTRAL("NEUTRAL"), LOW("LOW");

	private String	id;


	private Priority(final String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;

	}
}
