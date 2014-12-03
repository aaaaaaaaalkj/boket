package managementcards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import managementcards.cards.Card;
import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.IResult;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import common.IOutcome;
import common.IPlayer;

public class OutcomeImpl implements IOutcome {
	private final Map<IPlayer, IResult> results;

	public OutcomeImpl() {
		this.results = new HashMap<IPlayer, IResult>();
	}

  public final void computeResult(final IPlayer player, final List<@NonNull Card> communityCards) {
		results.put(player,
				new CatRec(player.getHand(), x(communityCards)).check());
	}

  private List<@NonNull Card> x(@Nullable final List<@NonNull Card> list) {
		if (null == list) {
			throw new IllegalArgumentException("will never happen");
		} else {
			return list;
		}
	}

	@Override
  public final boolean betterAs(final IPlayer p1, final IPlayer p2) {
		IResult r1 = results.get(p1);
		IResult r2 = results.get(p2);
		boolean res = r1.compareTo(r2) == 1;
		return res;
	}

	@Override
  public final IResult getResult(final IPlayer player) {
		return results.get(player);
	}

}
