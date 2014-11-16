package strategy.conditions;

import strategy.ISituation;

public class UtilityCond implements ICondition {
	private final static double REGLER = 1;
	private final double min;
	private final double max;

	public UtilityCond(double min, double max) {
		this.min = min * REGLER;
		this.max = max * REGLER;
	}

	public static UtilityCond call() {
		return new UtilityCond(.0, .05);
	}

	public static UtilityCond raiseLittle() {
		return new UtilityCond(.05, .1);
	}

	public static UtilityCond raiseLMore() {
		return new UtilityCond(.1, .2);
	}

	public static UtilityCond raiseBig() {
		return new UtilityCond(.2, .3);
	}

	public static UtilityCond raiseAllIn() {
		return new UtilityCond(.3, 1);
	}

	@Override
	public boolean eval(ISituation sit) {
		throw new IllegalStateException("not implemented yet");
	}

}
