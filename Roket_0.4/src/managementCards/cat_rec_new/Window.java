package managementCards.cat_rec_new;

import static managementCards.cards.Rank.Ace;
import static managementCards.cards.Rank.Eight;
import static managementCards.cards.Rank.Five;
import static managementCards.cards.Rank.Four;
import static managementCards.cards.Rank.Jack;
import static managementCards.cards.Rank.King;
import static managementCards.cards.Rank.Nine;
import static managementCards.cards.Rank.Queen;
import static managementCards.cards.Rank.Seven;
import static managementCards.cards.Rank.Six;
import static managementCards.cards.Rank.Ten;
import static managementCards.cards.Rank.Three;
import static managementCards.cards.Rank.Two;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import managementCards.cards.Rank;

import org.eclipse.jdt.annotation.NonNull;

import tools.Tools;

public enum Window {
	Five_High(Five, Four, Three, Two, Ace),
	Six_High(Six, Five, Four, Three, Two),
	Seven_High(Seven, Six, Five, Four, Three),
	Eight_High(Eight, Seven, Six, Five, Four),
	Nine_High(Nine, Eight, Seven, Six, Five),
	Ten_High(Ten, Nine, Eight, Seven, Six),
	Jack_High(Jack, Ten, Nine, Eight, Seven),
	Queen_High(Queen, Jack, Ten, Nine, Eight),
	King_High(King, Queen, Jack, Ten, Nine),
	Ace_High(Ace, King, Queen, Jack, Ten);

	private final List<Rank> list;

	private static final List<Window> VALUES_DESC;
	static {
		@SuppressWarnings("null")
		@NonNull
		Window[] values = values();
		List<Window> desc = Tools.asList(values);
		Collections.reverse(desc);
		VALUES_DESC = Tools.unmodifiableList(desc);
	}

	public static List<Window> getDescValues() {
		return VALUES_DESC;
	}

	public boolean contains(Rank rank) {
		return list.contains(rank);
	}

	public boolean contains(Rank rank, Rank rank2) {
		return list.contains(rank) && list.contains(rank2);
	}

	@SuppressWarnings("null")
	Window(Rank... ranks) {
		list = Tools.unmodifiableList(Tools.asList(ranks));
	}

	public List<Rank> getRanks() {
		return list;
	}

	public boolean applies(Collection<Rank> ranks) {
		return list.stream()
				.filter(ranks::contains)
				.count() == list.size();
	}

}
