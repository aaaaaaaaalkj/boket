package strategy.conditions;

import strategy.ISituation;

public interface ICondition {
	public boolean eval(ISituation sit);

	public static class AndCondition implements ICondition {
		ICondition first;
		ICondition second;

		public AndCondition(ICondition first, ICondition second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public boolean eval(ISituation sit) {
			return first.eval(sit) && second.eval(sit);
		}

		@Override
		public String toString() {
			return "(" + first + ") AND (" + second + ")";
		}
	}

	public static class OrCondition implements ICondition {
		ICondition first;
		ICondition second;

		public OrCondition(ICondition first, ICondition second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public boolean eval(ISituation sit) {
			return first.eval(sit) || second.eval(sit);
		}

		@Override
		public String toString() {
			return "(" + first + ") OR (" + second + ")";
		}
	}

	default ICondition or(final ICondition other) {
		return new OrCondition(this, other);
	}

	default ICondition and(final ICondition other) {
		return new AndCondition(this, other);
	}

}
