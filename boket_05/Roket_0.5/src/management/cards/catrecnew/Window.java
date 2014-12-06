package management.cards.catrecnew;

import static management.cards.cards.Rank.Ace;
import static management.cards.cards.Rank.Eight;
import static management.cards.cards.Rank.Five;
import static management.cards.cards.Rank.Four;
import static management.cards.cards.Rank.Jack;
import static management.cards.cards.Rank.King;
import static management.cards.cards.Rank.Nine;
import static management.cards.cards.Rank.Queen;
import static management.cards.cards.Rank.Seven;
import static management.cards.cards.Rank.Six;
import static management.cards.cards.Rank.Ten;
import static management.cards.cards.Rank.Three;
import static management.cards.cards.Rank.Two;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import management.cards.cards.Rank;

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

  public boolean contains(final Rank rank) {
    return list.contains(rank);
  }

  public boolean contains(final Rank rank, final Rank rank2) {
    return list.contains(rank) && list.contains(rank2);
  }

  @SuppressWarnings("null")
  Window(final Rank... ranks) {
    list = Tools.unmodifiableList(Tools.asList(ranks));
  }

  public List<Rank> getRanks() {
    return list;
  }

  public boolean applies(final Collection<Rank> ranks) {
    return Tools.contains(list, ranks);
  }

  public int match(final Collection<Rank> ranks) {
    return Tools.match(list, ranks);
  }
}
