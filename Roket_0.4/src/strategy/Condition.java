package strategy;

import java.util.ArrayList;
import java.util.List;

import strategy.conditions.ISituation;

public class Condition {
	private final ConditionType type;
	private final Condition left, right;

	private final Integer intParam;
	private final Double doubleParam;

	private Condition(ConditionType type, Integer intParam, Double doubleParam) {
		this.type = type;
		this.intParam = intParam;
		this.doubleParam = doubleParam;
		this.left = null;
		this.right = null;
	}

	private Condition(ConditionType type, Condition c1, Condition c2) {
		this.type = type;
		this.left = c1;
		this.right = c2;
		this.intParam = null;
		this.doubleParam = null;
	}

	public static Condition AND(Condition c1, Condition c2) {
		return new Condition(ConditionType.AND, c1, c2);
	}

	public static Condition OR(Condition c1, Condition c2) {
		return new Condition(ConditionType.OR, c1, c2);
	}

	public static Condition newInstance(ConditionType type) {
		return new Condition(type, (Integer) null, null);
	}

	public static Condition newInstance(ConditionType type, Double d) {
		return new Condition(type, null, d);
	}

	public static Condition newInstance(ConditionType type, int i) {
		return new Condition(type, i, null);
	}

	public static final Condition TRUE = newInstance(ConditionType.TRUE);
	public static final Condition FALSE = newInstance(ConditionType.FALSE);
	public static final Condition NOTHING = newInstance(ConditionType.NOTHING);
	public static final Condition SUITED = newInstance(ConditionType.SUITED);
	public static final Condition CONNECTOR = newInstance(ConditionType.CONNECTOR);
	public static final Condition SUITED_CONNECTOR = newInstance(ConditionType.SUITED_CONNECTOR);
	public static final Condition PREFLOP = newInstance(ConditionType.PREFLOP);
	public static final Condition FLOP = newInstance(ConditionType.FLOP);
	public static final Condition TURN = newInstance(ConditionType.TURN);
	public static final Condition RIVER = newInstance(ConditionType.RIVER);
	public static final Condition TEN_AND_HIGHER = newInstance(ConditionType.TEN_AND_HIGHER);
	public static final Condition POCKET_PAIR = newInstance(ConditionType.POCKET_PAIR);
	public static final Condition OESD = newInstance(ConditionType.OESD);
	public static final Condition GUTSHOT = newInstance(ConditionType.GUTSHOT);
	public static final Condition DOUBLE_GUTSHOT = newInstance(ConditionType.DOUBLE_GUTSHOT);
	public static final Condition FLUSH_DRAW = newInstance(ConditionType.FLUSH_DRAW);
	public static final Condition MONSTER_DRAW = newInstance(ConditionType.MONSTER_DRAW);
	public static final Condition GOOD_SET = newInstance(ConditionType.GOOD_SET);
	public static final Condition GOOD_TWO_PAIR = newInstance(ConditionType.GOOD_TWO_PAIR);
	public static final Condition TWO_PAIR = newInstance(ConditionType.TWO_PAIR);
	public static final Condition NO_FLUSH_DANGER = newInstance(ConditionType.NO_FLUSH_DANGER);
	public static final Condition NO_STRAIGHT_DANGER = newInstance(ConditionType.NO_STRAIGHT_DANGER);
	public static final Condition NO_FULL_HOUSE_DANGER = newInstance(ConditionType.NO_FULL_HOUSE_DANGER);
	public static final Condition FLUSH_DANGER = newInstance(ConditionType.FLUSH_DANGER);
	public static final Condition STRAIGHT_DANGER = newInstance(ConditionType.STRAIGHT_DANGER);
	public static final Condition FULL_HOUSE_DANGER = newInstance(ConditionType.FULL_HOUSE_DANGER);
	public static final Condition RAINBOW = newInstance(ConditionType.RAINBOW);
	public static final Condition FACES_IN_FLOP = newInstance(ConditionType.FACES_IN_FLOP);
	public static final Condition NO_FACES_IN_FLOP = newInstance(ConditionType.NO_FACES_IN_FLOP);
	public static final Condition THREE_OF_A_KIND = newInstance(ConditionType.THREE_OF_A_KIND);
	public static final Condition STRAIGHT = newInstance(ConditionType.STRAIGHT);
	public static final Condition GOOD_STRAIGHT = newInstance(ConditionType.GOOD_STRAIGHT);
	public static final Condition BEST_STRAIGHT = newInstance(ConditionType.BEST_STRAIGHT);
	public static final Condition FLUSH = newInstance(ConditionType.FLUSH);
	public static final Condition FULL_HOUSE = newInstance(ConditionType.FULL_HOUSE);
	public static final Condition FOUR_OF_A_KIND = newInstance(ConditionType.FOUR_OF_A_KIND);
	public static final Condition STRAIGHT_FLUSH = newInstance(ConditionType.STRAIGHT_FLUSH);

	public static final Condition TO_PAY_LESS_OR_EQ_TO(int numBB) {
		return newInstance(ConditionType.TO_PAY_LESS_OR_EQ_TO, numBB);
	}

	public static final Condition POT_LESS_OR_EQ_TO(int numBB) {
		return newInstance(ConditionType.POT_LESS_OR_EQ_TO, numBB);
	}

	public static final Condition NUM_ACTIVE_PLAYERS(int value) {
		return newInstance(ConditionType.NUM_ACTIVE_PLAYERS, value);
	}

	public static final Condition STRAIGHT_DANGER_HIGHER(int value) {
		return newInstance(ConditionType.STRAIGHT_DANGER_HIGHER, value);
	}

	public static final Condition CONTRIBUTION_LESS_THAN(double value) {
		return newInstance(ConditionType.CONTRIBUTION, value);
	}

	public String toString() {
		if (type == ConditionType.AND) {
			return left + " AND " + right;
		}
		if (type == ConditionType.OR) {
			return left + " OR " + right;
		}

		return type.toString()
				+ (intParam != null ? "(" + intParam + ")"
						: doubleParam != null ? "(" + doubleParam + ")" : "");
	}

	/**
	 * Assumes that this condition is valid for the given situation. The method
	 * gathers all sub-conditions which are responsible for this condition
	 * beeing valid for the given situation. Recursively traverses the
	 * condition-tree. For AND-nodes it follows both subtrees. For OR-nodes it
	 * follows only those sub-trees which led to the validity of this condition.
	 * 
	 * @param situation
	 * @return list of valid conditions for the given situation.
	 */
	public List<Condition> getConditions(ISituation situation) {
		List<Condition> res = new ArrayList<>();
		if (type == ConditionType.AND) {
			res.addAll(left.getConditions(situation));
			res.addAll(right.getConditions(situation));
		} else if (type == ConditionType.OR) {
			if (left.eval(situation)) {
				res.addAll(left.getConditions(situation));
			} else if (right.eval(situation)) {
				res.addAll(right.getConditions(situation));
			}
		} else {
			res.add(this);
		}
		return res;
	}

	public String toString(ISituation situation) {
		if (type == ConditionType.AND) {
			return left.toString(situation) + " AND "
					+ right.toString(situation);
		}
		if (type == ConditionType.OR) {
			if (left.eval(situation))
				return left.toString(situation);
			if (right.eval(situation))
				return right.toString(situation);
			// return left + " OR " + right;
		}

		return type.toString()
				+ (intParam != null ? "(" + intParam + ")"
						: doubleParam != null ? "(" + doubleParam + ")" : "");
	}

	public Condition and(final Condition cond) {
		return AND(this, cond);
	}

	public Condition or(final Condition cond) {
		return OR(this, cond);
	}

	public boolean eval(ISituation sit) {
		boolean res;

		if (type == ConditionType.OR) {
			res = left.eval(sit) || right.eval(sit);
		} else if (type == ConditionType.AND) {
			res = left.eval(sit) && right.eval(sit);
		} else {
			res = sit.isApplicably(type);
		}

		return res;
	}

}
