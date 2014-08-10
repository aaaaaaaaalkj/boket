package common;


public final class Player2 implements PlayerId {
	private final String name;

	public Player2(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

}
