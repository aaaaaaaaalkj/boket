package strategy;

import common.Round;

public enum ConditionType {
	NOTHING, AND, OR, TRUE, FALSE,

	PREFLOP, FLOP, TURN, RIVER,

	SUITED, CONNECTOR, TEN_AND_HIGHER, POCKET_PAIR, SUITED_CONNECTOR,

	RAINBOW, FACES_IN_FLOP, NO_FACES_IN_FLOP,

	CONTRIBUTION, TO_PAY_LESS_OR_EQ_TO, POT_LESS_OR_EQ_TO, NUM_ACTIVE_PLAYERS,

	NO_FLUSH_DANGER, NO_STRAIGHT_DANGER, NO_FULL_HOUSE_DANGER, FLUSH_DANGER, STRAIGHT_DANGER, STRAIGHT_DANGER_HIGHER, FULL_HOUSE_DANGER,

	OESD, GUTSHOT, DOUBLE_GUTSHOT, FLUSH_DRAW, MONSTER_DRAW,

	GOOD_SET, GOOD_TWO_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH, GOOD_STRAIGHT, BEST_STRAIGHT;

	public static ConditionType fromRound(Round round) {
		switch (round) {
		case FLOP:
			return FLOP;
		case PREFLOP:
			return PREFLOP;
		case RIVER:
			return RIVER;
		case TURN:
			return TURN;
		default:
			throw new IllegalArgumentException("Unknown round: " + round);
		}
	}
}
