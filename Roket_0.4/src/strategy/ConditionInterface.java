package strategy;

import java.util.List;

public interface ConditionInterface {
	public List<ConditionInterface> getConditions(Situation situation);

	public ConditionInterface and(final ConditionInterface cond);

	public ConditionInterface or(final ConditionInterface cond);

	public boolean eval(Situation sit);
}
