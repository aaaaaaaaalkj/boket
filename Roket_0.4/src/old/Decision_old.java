package old;

import strategy.TypeOfDecision;
import managementPayments.AmountOfJetons;

public class Decision_old {
	public static Decision_old FOLD = new Decision_old(TypeOfDecision.FOLD,
			AmountOfJetons.ZERO);
	public static Decision_old CHECK = new Decision_old(TypeOfDecision.CALL,
			AmountOfJetons.ZERO);

	// Call Any
	public static Decision_old CALL = new Decision_old(TypeOfDecision.CALL,
			new AmountOfJetons(9999));

	public static Decision_old CALL(AmountOfJetons value) {
		return new Decision_old(TypeOfDecision.CALL, value);
	}

	public static Decision_old RAISE(AmountOfJetons amount) {
		return new Decision_old(TypeOfDecision.RAISE_QUARTER_POT, amount);
	}

	public static Decision_old RAISE(int bb) {
		return new Decision_old(TypeOfDecision.RAISE_QUARTER_POT,
				new AmountOfJetons(bb * 2));
	}

	public final TypeOfDecision type;
	public final AmountOfJetons value;

	public Decision_old(TypeOfDecision type, AmountOfJetons bb) {
		this.type = type;
		this.value = bb;
	}

	public boolean fold() {
		return type == TypeOfDecision.FOLD;
	}

	public boolean call() {
		return type == TypeOfDecision.CALL;
	}

	public boolean raise() {
		return type == TypeOfDecision.RAISE_QUARTER_POT;
	}

	@Override
	public String toString() {
		switch (type) {
		case CALL:
			return "CALL";
		case FOLD:
			return "FOLD";
		case RAISE_QUARTER_POT:
			return "RAISE " + value;
		case ALL_IN:
			return "ALL_IN (" + value + ")";
		default:
			return type + " is not implemented yet";
		}
	}

}
