package strategy.conditions;

public interface ICondition {
	public boolean eval(ISituation sit);

	default ICondition or(final ICondition other) {
		return (sit -> eval(sit) || other.eval(sit));
	}

	default ICondition and(final ICondition other) {
		return (sit -> eval(sit) && other.eval(sit));
	}

}
