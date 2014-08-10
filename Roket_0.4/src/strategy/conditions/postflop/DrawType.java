package strategy.conditions.postflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum DrawType implements ICondition {
	NONE, GUTSHOT, OESD, DOUBLEGUTSHOT, FLUSH_DRAW, MONSTER_DRAW;
	public static final EnumSet<DrawType> VALUES = EnumSet
			.allOf(DrawType.class);
	boolean bothCardsInvolved;
	double dangerOfBetterKicker; // [0,1]
	
	

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getDraw();
	}



}
