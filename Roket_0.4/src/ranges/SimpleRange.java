package ranges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import managementCards.cards.Card;
import managementCards.cards.Rank;

public class SimpleRange implements Range {
	private final EnumSet<ElementRange> elements;

	@SuppressWarnings("null")
	public SimpleRange() {
		this(EnumSet.noneOf(ElementRange.class));
	}

	public SimpleRange(EnumSet<ElementRange> elements) {
		this.elements = elements;
	}

	public void add(ElementRange e) {
		elements.add(e);
	}

	public boolean remove(ElementRange e) {
		return elements.remove(e);
	}

	public static void main(String[] args) {
		code4Enum2();
	}

	@SuppressWarnings("unused")
	private static final void code4Enum() {
		List<Card> cards = Card.getAllCards();
		for (int i = cards.size() - 1; i >= 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				Card c1 = cards.get(i);
				Card c2 = cards.get(j);
				System.out.println(c1.shortString2() + "_" + c2.shortString()
						+ "(" + c1.getRank() + "," + c1.getSuit() + ","
						+ c2.getRank() + "," + c2.getSuit() + "),");
			}
		}
	}

	private static final void code4Enum2() {
		List<Rank> ranks = new ArrayList<>(Rank.VALUES);
		Collections.reverse(ranks);

		String s = "";

		for (int i = 0; i < ranks.size(); i++) {
			Rank r1 = ranks.get(i);
			String s1 = r1.shortString2();
			String s1_ = r1.shortString();
			s += s1 + s1_ + "(" + r1 + "," + r1 + ",false),\n";

			for (int j = i + 1; j < ranks.size(); j++) {
				Rank r2 = ranks.get(j);
				String s2 = r2.shortString();
				s += s1 + s2 + "(" + r1 + "," + r2 + ",true),\n";
				s += s1 + s2 + "o(" + r1 + "," + r2 + ",false),\n";
			}

		}

		System.out.println(s);

	}

	@Override
	public boolean contains(ElementRange r) {
		return elements.contains(r);
	}

	public int size() {
		return elements.size();
	}

	public void addAll(SimpleRange other) {
		this.elements.addAll(other.elements);
	}
}
