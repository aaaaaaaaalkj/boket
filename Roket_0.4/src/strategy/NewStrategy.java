package strategy;

import common.Round;

public class NewStrategy implements IStrategy {
	private TypeOfDecision[] preflop = new TypeOfDecision[PreflopSelector.size];
	private TypeOfDecision[] postflop = new TypeOfDecision[PostflopSelector.size];

	private NewStrategy() {

		for (int i = 0; i < preflop.length; i++) {
			preflop[i] = TypeOfDecision.random();
		}
		for (int i = 0; i < postflop.length; i++) {
			preflop[i] = TypeOfDecision.random();
		}

	}

	public static NewStrategy random() {
		NewStrategy s = new NewStrategy();
		return s;
	}

	public TypeOfDecision getPreflop(ISelector sel) {
		return preflop[sel.getPosition()];
	}

	public TypeOfDecision getPostflop(ISelector sel) {
		return preflop[sel.getPosition()];
	}

	public void setPreflop(ISelector sel, TypeOfDecision dec) {
		preflop[sel.getPosition()] = dec;
	}

	public void setPostflop(ISelector sel, TypeOfDecision dec) {
		postflop[sel.getPosition()] = dec;
	}

	private TypeOfDecision get(PreflopSelector sel) {
		return preflop[sel.getPosition()];
	}

	private TypeOfDecision get(PostflopSelector sel) {
		return postflop[sel.getPosition()];
	}

	@Override
	public TypeOfDecision decide(ISituation situation) {
		if (Round.PREFLOP == situation.getRound()) {
			return get(new PreflopSelector(
					situation.getContribution(),
					situation.getNumActive(),
					situation.getPot(),
					situation.getConnector(),
					situation.getSuit()));
		} else {
			return get(new PostflopSelector(
					situation.getContribution(),
					situation.getNumActive(),
					situation.getPot(),
					situation.getCombo(),
					situation.getPairBasedDanger(),
					situation.getFlushDanger(),
					situation.getStraightDanger(),
					situation.getDraw()));
		}
	}
}
