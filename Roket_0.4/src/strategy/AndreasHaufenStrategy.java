package strategy;

public class AndreasHaufenStrategy implements IStrategy {
	int foldHaufen = 0;
	int callHaufen = 0;
	int raiseHaufen = 0;

	@Override
	public TypeOfDecision decide(ISituation situation) {
		situation.getContribution();

		situation.getNumActive();

		situation.getPot();

		callHaufen += situation.getCathegory().ordinal();
		raiseHaufen += situation.getCathegory().ordinal();

		foldHaufen += situation.getStraightDanger().ordinal();
		callHaufen -= situation.getStraightDanger().ordinal();
		raiseHaufen -= situation.getStraightDanger().ordinal();

		situation.getFlushDanger();

		situation.getPairBasedDanger();

		situation.getDraw();

		situation.getConnector();

		situation.getSuit();

		situation.getRound();

		return null;
	}
}
