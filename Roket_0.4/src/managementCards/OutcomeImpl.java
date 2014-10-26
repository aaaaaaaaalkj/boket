package managementCards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import managementCards.cards.Card;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.IResult;

import common.IOutcome;
import common.IPlayer;

public class OutcomeImpl implements IOutcome {
	private final Map<IPlayer, IResult> results;

	public OutcomeImpl() {
		this.results = new HashMap<IPlayer, IResult>();
	}

	public void computeResult(IPlayer player, List<Card> communityCards) {
		results.put(player,
				new Cat_Rec(player.getHand(), communityCards).check());
	}

	@Override
	public boolean betterAs(IPlayer p1, IPlayer p2) {
		IResult r1 = results.get(p1);
		IResult r2 = results.get(p2);
		boolean res = r1.compareTo(r2) == 1;
		return res;
	}

	@Override
	public IResult getResult(IPlayer player) {
		return results.get(player);
	}

}
