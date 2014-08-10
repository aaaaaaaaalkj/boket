package managementState;

/**
 * ACTIVE = not folded and has still jetons in his stack ALL_IN = not folded and
 * has no jetons in his stack FOLDED = ... SITTING_OUT = FOLDED
 * 
 * @author Combat-Ready
 * 
 */
public enum PlayerState {
	ACTIVE, ALL_IN, FOLDED, SITTING_OUT;

	public boolean inGame() {
		return this == ALL_IN || this == ACTIVE;
	}

	public boolean notActive() {
		return this != ACTIVE;
	}

	public boolean isActive() {
		return this == ACTIVE;
	}
}
