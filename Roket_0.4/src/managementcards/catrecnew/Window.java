package managementcards.catrecnew;

import static managementcards.cards.Rank.Ace;
import static managementcards.cards.Rank.Eight;
import static managementcards.cards.Rank.Five;
import static managementcards.cards.Rank.Four;
import static managementcards.cards.Rank.Jack;
import static managementcards.cards.Rank.King;
import static managementcards.cards.Rank.Nine;
import static managementcards.cards.Rank.Queen;
import static managementcards.cards.Rank.Seven;
import static managementcards.cards.Rank.Six;
import static managementcards.cards.Rank.Ten;
import static managementcards.cards.Rank.Three;
import static managementcards.cards.Rank.Two;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import managementcards.cards.Rank;

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
