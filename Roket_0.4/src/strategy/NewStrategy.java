package strategy;

import org.eclipse.jdt.annotation.NonNull;

import common.Round;

public class NewStrategy implements IStrategy {
	private TypeOfDecision @NonNull [] preflop = new TypeOfDecision @NonNull [PreflopSelector.SIZE];
	private TypeOfDecision @NonNull [] postflop = new TypeOfDecision @NonNull [PostflopSelector.SIZE];

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
		@SuppressWarnings("null")
		@NonNull
		TypeOfDecision x = preflop[sel.getPosition()];
		return x;
	}

	public TypeOfDecision getPostflop(ISelector sel) {
		@SuppressWarnings("null")
		@NonNull
		TypeOfDecision x = preflop[sel.getPosition()];
		return x;
	}

	public void setPreflop(ISelector sel, TypeOfDecision dec) {
		preflop[sel.getPosition()] = dec;
	}

	public void setPostflop(ISelector sel, TypeOfDecision dec) {
		postflop[sel.getPosition()] = dec;
	}

	private TypeOfDecision get(PreflopSelector sel) {
		@SuppressWarnings("null")
		@NonNull
		TypeOfDecision x = preflop[sel.getPosition()];
		return x;
	}

	private TypeOfDecision get(PostflopSelector sel) {
		@SuppressWarnings("null")
		@NonNull
		TypeOfDecision x = postflop[sel.getPosition()];
		return x;
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
					situation.getCathegory(),
					situation.getPairBasedDanger(),
					situation.getFlushDanger(),
					situation.getStraightDanger(),
					situation.getDraw()));
		}
	}
}
