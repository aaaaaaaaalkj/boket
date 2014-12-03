package strategy.conditions;

import strategy.ISituation;

public interface ICondition {
  boolean eval(ISituation sit);

  public static class AndCondition implements ICondition {
    private ICondition first;
    private ICondition second;

    public AndCondition(final ICondition first, final ICondition second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public final boolean eval(final ISituation sit) {
      return first.eval(sit) && second.eval(sit);
    }

    @Override
    public final String toString() {
      return "(" + first + ") AND (" + second + ")";
    }
  }

  public static class OrCondition implements ICondition {
    private ICondition first;
    private ICondition second;

    public OrCondition(final ICondition first, final ICondition second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public final boolean eval(final ISituation sit) {
      return first.eval(sit) || second.eval(sit);
    }

    @Override
    public final String toString() {
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
