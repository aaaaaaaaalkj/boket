package strategy;

import java.util.ArrayList;
import java.util.List;

import managementPayments.AmountOfJetons;
import tools.MyStringBuilder;

public class Decision {
	private List<Condition> conditions;
	private TypeOfDecision decision;
	private PlayerDecision modified;
	private AmountOfJetons amount;

	public Decision() {
		conditions = new ArrayList<>();
		conditions.add(Condition.NOTHING);
		decision = TypeOfDecision.FOLD;
		amount = AmountOfJetons.ZERO;
	}

	public void setType(TypeOfDecision d) {
		decision = d;
	}

	public void setConditions(List<Condition> list) {
		conditions = list;
	}

	public TypeOfDecision getTypeOfDecision() {
		return decision;
	}

	public PlayerDecision getModified() {
		return modified;
	}

	public void setModified(PlayerDecision modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		MyStringBuilder s = new MyStringBuilder();
		for (Condition c : conditions) {
			s.add(c);
			s.add(" and ");
		}
		s.remove(" and ");
		s.add(" -> ");
		s.add(decision);
		s.add(" -> ");
		s.add(modified + " " + amount);

		return s.toString();
	}

	public void setAmount(AmountOfJetons amount) {
		this.amount = amount;
	}

	public AmountOfJetons getAmount() {
		return this.amount;
	}
}
