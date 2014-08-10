package strategy.conditions;

import strategy.IStrategy;
import strategy.TypeOfDecision;

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

	public TypeOfDecision preflop(ISelector sel) {
		return preflop[sel.getPosition()];
	}

	public TypeOfDecision postflop(ISelector sel) {
		return preflop[sel.getPosition()];
	}

	public void setPreflop(ISelector sel, TypeOfDecision dec) {
		preflop[sel.getPosition()] = dec;
	}

	public void setPostflop(ISelector sel, TypeOfDecision dec) {
		postflop[sel.getPosition()] = dec;
	}

	@Override
	public TypeOfDecision decide(ISituation situation) {
		if (Round.PREFLOP == situation.getRound()) {
			PreflopSelector sel = new PreflopSelector();
			sel.setConnector(situation.getConnectorType());
			sel.setContribution(situation.getContribution());
			sel.setNumActive(situation.getNumActivePlayers());
			sel.setPot(situation.getPot());
			sel.setSuited(situation.getSuit());

		} else {

			PostflopSelector sel2 = new PostflopSelector();

			sel2.setCombo(situation.getCombo());
			sel2.setDanger(situation.getDanger());
			sel2.setDraw(situation.getDraw());
			sel2.setNumActive(situation.getNumActivePlayers());
			sel2.setPot(situation.getPot());
			sel2.setContribution(situation.getContribution());
		}
		return null;
	}
}
