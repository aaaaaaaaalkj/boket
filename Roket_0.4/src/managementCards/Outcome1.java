package managementCards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import managementCards.cards.Card;
import managementCards.cards.CommunityCards;
import managementCards.cards.Hand;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.Result;

import common.Outcome;
import common.PlayerId;

public class Outcome1 implements Outcome {
	private final Map<PlayerId, Result> results;

	public Outcome1() {
		this.results = new HashMap<PlayerId, Result>();
	}

	public void computeResult(PlayerId player, Hand hand,
			CommunityCards communityCards) {
		List<Optional<Card>> set = new ArrayList<>();
		set.add(Optional.of(hand.getFirst()));
		set.add(Optional.of(hand.getSecond()));
		set.add(communityCards.getFlop1());
		set.add(communityCards.getFlop2());
		set.add(communityCards.getFlop3());
		set.add(communityCards.getTurn());
		set.add(communityCards.getRiver());

		List<Card> present = set.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		results.put(player, Cat_Rec.check(present));
	}

	@Override
	public boolean betterAs(PlayerId p1, PlayerId p2) {
		Result r1 = results.get(p1);
		Result r2 = results.get(p2);
		boolean res = r1.compareTo(r2) == 1;
		return res;
	}

}
