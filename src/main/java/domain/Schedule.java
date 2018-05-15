
package domain;

public enum Schedule {

	DAILY("DAILY"), WEEKLY("WEEKLY"), MONTHLY("MONTHLY");

	private String	id;


	private Schedule(final String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;

	}
}
