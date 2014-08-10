package managementCards.cat_rec_new;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;

public class CardFeatures {
	final EnumMap<Suit, List<Rank>> flush; // flush
	final List<Rank> cardNums; // Pair-Based
	final List<Rank> ranks; // straight
	private final EnumMap<Freq, List<Rank>> orderedFreq; // Pair-Based

	public CardFeatures(Collection<Card> cards) {
		// straight
		ranks = new ArrayList<>();
		for (Card c : cards) {
			ranks.add(c.getRank());
		}
		// PairBased
		cardNums = new ArrayList<>();
		for (Card c : cards) {
			cardNums.add(c.getRank());
		}

		orderedFreq = new EnumMap<>(Freq.class);
		for (Freq f : Freq.VALUES) {
			orderedFreq.put(f, new ArrayList<Rank>());
		}

		for (Rank c : Rank.VALUES) {
			Freq freq = Freq.values()[Collections.frequency(cardNums, c)];
			orderedFreq.get(freq).add(c);
		}

		// Flush
		this.flush = new EnumMap<Suit, List<Rank>>(Suit.class);
		for (Suit c : Suit.VALUES) {
			this.flush.put(c, new ArrayList<Rank>());
		}

		for (Card c : cards) {
			this.flush.get(c.getSuit()).add(c.getRank());
		}
		for (Suit c : Suit.VALUES) {
			Collections.sort(this.flush.get(c));
			Collections.reverse(this.flush.get(c));
		}
	}

	protected List<Rank> getTop() {
		Collections.sort(cardNums);
		List<Rank> res = new ArrayList<>();
		if (!cardNums.isEmpty())
			res.add(cardNums.remove(cardNums.size() - 1));
		return res;
	}

	protected List<Rank> getTop(int num) {
		Collections.sort(cardNums);
		List<Rank> res = new ArrayList<>();
		for (int i = 0; i < num && !cardNums.isEmpty(); i++)
			res.add(cardNums.remove(cardNums.size() - 1));
		return res;
	}

	protected List<Rank> extract(Freq f) {
		List<Rank> list = orderedFreq.get(f);
		Rank n = list.remove(list.size() - 1);
		List<Rank> res = new ArrayList<>();
		for (int i = 0; i < f.value; i++) {
			res.add(n);
		}
		cardNums.removeAll(res);
		return res;
	}

	protected int num(Freq f) {
		return orderedFreq.get(f).size();
	}

	protected boolean has(Freq f) {
		return num(f) > 0;
	}
}
