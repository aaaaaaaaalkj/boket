package managementState;

import managementPaymentsNew.decisions.DecisionType;

public interface IStateManagement {

	IGameState step();

	void update(DecisionType d);

	default void fold() {
		update(DecisionType.FOLD);
	}

	default void call() {
		update(DecisionType.CALL);
	}

	default void raised() {
		update(DecisionType.RAISE);
	}

	default void allin() {
		update(DecisionType.ALL_IN);
	}
}
