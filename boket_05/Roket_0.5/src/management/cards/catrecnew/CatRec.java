package management.cards.catrecnew;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static management.cards.cards.Rank.Ace;
import static management.cards.cards.Rank.Jack;
import static management.cards.cards.Rank.Ten;
import static management.cards.cards.Rank.Two;
import static management.cards.catrecnew.Cathegory.FOUR_OF_A_KIND;
import static management.cards.catrecnew.Cathegory.FULL_HOUSE;
import static management.cards.catrecnew.Cathegory.HIGH_CARD;
import static management.cards.catrecnew.Cathegory.PAIR;
import static management.cards.catrecnew.Cathegory.THREE_OF_A_KIND;
import static management.cards.catrecnew.Cathegory.TWO_PAIR;
import static management.cards.catrecnew.Freq.FOUR;
import static management.cards.catrecnew.Freq.ONE;
import static management.cards.catrecnew.Freq.THREE;
import static management.cards.catrecnew.Freq.TWO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import management.cards.cards.Card;
import management.cards.cards.Rank;
import management.cards.cards.Suit;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.LoggerFactory;

import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;
import tools.Tools;

public final class CatRec implements ICatRec {
  @SuppressWarnings("null")
  static final org.slf4j.Logger LOG = LoggerFactory
      .getLogger(CatRec.class);

  public CatRec() {
    // final List<@NonNull Card> hand,
    // final List<@NonNull Card> communityCards
  }

  /**
   * @Parameter cards - list of cards. first two are hand-cards. last 5 are community cards
   */
  @Override
  public ResultImpl check(List<Card> cards) {
    List<Card> all = new ArrayList<>(cards);
    Collections.sort(all);
    Collections.reverse(all);

    List<Rank> ranks = Tools.map(all, Card::getRank);

    ResultImpl pairB = getPairBasedResult(ranks);

    ResultImpl flushB = getFlushOrStraightResult(all);

    return (flushB.compareTo(pairB) > 0) ? flushB : pairB;
  }

  private static final int COUNT_BEST_CARDS = 5;

  private ResultImpl getPairBasedResult(final List<Rank> ranks) {
    List<@NonNull List<@NonNull Rank>> choosenCards;
    if (has(ranks, FOUR)) {
      choosenCards = Tools
          .asList(extract(ranks, FOUR), getTop(ranks, 1));
      return result(FOUR_OF_A_KIND, choosenCards);
    }
    if (num(ranks, THREE) > 1) {
      choosenCards = Tools.asList(extract(ranks, THREE),
          extract(ranks, THREE));
      return result(FULL_HOUSE, choosenCards);
    }
    if ((has(ranks, THREE) && has(ranks, TWO))) {
      choosenCards = Tools.asList(extract(ranks, THREE),
          extract(ranks, TWO));
      return result(FULL_HOUSE, choosenCards);
    }
    if (has(ranks, THREE)) {
      choosenCards = Tools.asList(extract(ranks, THREE),
          getTop(ranks, 2));
      return result(THREE_OF_A_KIND, choosenCards);
    }
    if (num(ranks, TWO) > 1) {
      choosenCards = Tools.asList(extract(ranks, TWO),
          extract(ranks, TWO), getTop(ranks, 1));
      return result(TWO_PAIR, choosenCards);
    }
    if (has(ranks, TWO)) {
      choosenCards = Tools.asList(extract(ranks, TWO),
          getTop(ranks, COUNT_BEST_CARDS - TWO.getValue()));
      return result(PAIR, choosenCards);
    }
    if (has(ranks, ONE)) {
      choosenCards = Tools.asList(getTop(ranks, COUNT_BEST_CARDS));
      return result(HIGH_CARD, choosenCards);
    }
    List<List<Rank>> list = Tools.emptyList();
    return result(HIGH_CARD, list);
  }

  // +++++++++++++++++++++++++

  private List<Rank> getTop(final List<Rank> ranks, final int num) {
    Collections.sort(ranks); // ascending
    Collections.reverse(ranks); // desc

    List<Rank> top = ranks.subList(0, Math.min(num, ranks.size()));
    List<Rank> res = new ArrayList<>(top);
    top.clear(); // remove elems from ranks

    return res;
  }

  private List<Rank> extract(final List<Rank> ranks, final Freq f) {
    List<Rank> list = Rank.VALUES.stream()
        .filter(c -> Collections.frequency(ranks, c) == f.getValue())
        .collect(Tools.toList());
    Collections.sort(list); // ascending
    Rank rank = list.get(list.size() - 1); // get last elem
    List<Rank> res = Tools.nCopies(f.getValue(), rank);
    ranks.removeAll(res);
    return res;
  }

  private long num(final List<Rank> ranks, final Freq f) {
    // Collections.sort(ranks);
    // long res = 0;
    // int count = 1;
    // for (int i = 1; i < ranks.size(); i++) {
    // if (ranks.get(i).equals(ranks.get(i - 1))) {
    // count++;
    // } else {
    // if (count == f.value) {
    // res++;
    // }
    // count = 1;
    // }
    // }
    // return res;

    return Rank.VALUES.stream()
        .filter(c -> Collections.frequency(ranks, c) == f.getValue())
        .count();

  }

  private boolean has(final List<Rank> ranks, final Freq f) {
    return num(ranks, f) > 0;
  }

  // +++++++++++++++++++++++++
  private ResultImpl result(final Cathegory cat,
      final List<List<Rank>> tieBreakers) {
    List<Rank> tie2 = Tools.first(COUNT_BEST_CARDS, Tools.flatten(tieBreakers));
    return new ResultImpl(cat, tie2);
  }

  private ResultImpl getFlushOrStraightResult(List<Card> all) {

    Map<Suit, List<Rank>> map;
    map = Tools.groupingBy(all, Card::getSuit, Card::getRank);

    Optional<ResultImpl> straightResult = Tools.empty();
    Optional<ResultImpl> flushResult = Tools.empty();
    Optional<ResultImpl> straightflushResult = Tools.empty();

    // Flush
    for (Suit c : Suit.values()) {
      if (map.containsKey(c) && map.get(c).size() >= COUNT_BEST_CARDS) {
        List<Rank> tieBreakers = new ArrayList<>(map.get(c)
            .subList(0, COUNT_BEST_CARDS));
        flushResult = Tools.of(new ResultImpl(Cathegory.FLUSH,
            tieBreakers));
        break;
      }
    }
    // Straight
    List<Rank> ranks2;
    ranks2 = Tools.map(all, Card::getRank);

    for (Window w : Window.getDescValues()) {
      if (w.applies(ranks2)) {
        straightResult = Tools.of(new ResultImpl(Cathegory.STRAIGHT, w
            .getRanks()));
        break;
      }
    }

    if (straightResult.isPresent() && flushResult.isPresent()) {
      // StraightFlush
      out: for (Suit c : Suit.VALUES) {
        for (Window w : Window.getDescValues()) {
          if (map.containsKey(c) && w.applies(map.get(c))) {
            straightflushResult = Tools.of(new ResultImpl(
                Cathegory.STRAIGHT_FLUSH,
                w.getRanks()));
            break out;
          }
        }
      }
    }
    if (straightflushResult.isPresent()) {
      return straightflushResult.get();
    } else if (flushResult.isPresent()) {
      return flushResult.get();
    } else if (straightResult.isPresent()) {
      return straightResult.get();
    } else {
      // Nothing
      return new ResultImpl(Cathegory.HIGH_CARD); // worst possible result
    }
  }

  private boolean checkOESD(List<Card> all) {
    Set<Rank> ranks = new HashSet<>();
    for (Card c : all) {
      ranks.add(c.getRank());
    }
    boolean res = false;
    for (Rank r = Two; r.lessThan(Jack); r = r.next()) {
      Rank forbidden1 = r.prev();
      Rank forbidden2 = r.next().next().next().next();

      boolean oesd = true;
      oesd &= !ranks.contains(forbidden1);
      oesd &= !ranks.contains(forbidden2);

      oesd &= ranks.contains(r);
      oesd &= ranks.contains(r.next());
      oesd &= ranks.contains(r.next().next());
      oesd &= ranks.contains(r.next().next().next());
      res |= oesd;
    }
    return res;
  }

  private boolean checkGutshot(List<Card> all) {
    @SuppressWarnings("null")
    Set<Rank> ranks = all.stream()
        .map(Card::getRank)
        .collect(toSet());

    boolean res = false;
    EnumSet<Rank> r2 = EnumSet.range(Two, Ten);
    r2.add(Ace);
    for (Rank r : r2) {
      boolean gutshot = true;
      int n = 0;
      n += ranks.contains(r.next(1)) ? 1 : 0;
      n += ranks.contains(r.next(2)) ? 1 : 0;
      n += ranks.contains(r.next(3)) ? 1 : 0;

      gutshot &= ranks.contains(r);
      gutshot &= ranks.contains(r.next(4));
      gutshot &= n == 2;

      res |= gutshot;

    }

    return res;
  }

  private static final int DRAWS_NEEDED_FOR_DOUBLE_GUTSHOT = 2;
  private static final int CARDS_IN_WINDOW_NEEDED_FOR_GUTSHOT = 4;

  public boolean checkDoubleGutshot2(List<Card> all) {
    Set<Rank> ranks = new HashSet<>();
    for (Card c : all) {
      ranks.add(c.getRank());
    }
    int count = 0;
    for (Window w : Window.getDescValues()) {
      int matchesInWindow = w.match(ranks);
      if (matchesInWindow == CARDS_IN_WINDOW_NEEDED_FOR_GUTSHOT) {
        count++;
      }
    }

    return count == DRAWS_NEEDED_FOR_DOUBLE_GUTSHOT;
  }

  @SuppressWarnings("null")
  private Boolean checkFlushDraw(List<Card> all) {
    return all.stream()
        .collect(groupingBy(Card::getSuit, counting()))
        .values().stream()
        .max(naturalOrder())
        .map(x -> x == 4)
        .orElse(Boolean.FALSE);
  }

  @Override
  public PairBasedDanger checkPairBasedDanger(List<Card> community) {
    List<Rank> ranks = Tools.map(community, Card::getRank);
    ResultImpl pairB = getPairBasedResult(ranks);
    Cathegory c = pairB.getCathegory();
    if (c == PAIR) {
      return PairBasedDanger.MODERATE;
    } else if (c == TWO_PAIR || c == THREE_OF_A_KIND) {
      return PairBasedDanger.HIGH;
    } else if (c == FULL_HOUSE || c == FOUR_OF_A_KIND) {
      return PairBasedDanger.CERTAIN_COMBO;
    } else {
      return PairBasedDanger.NONE;
    }
  }

  /**
   * measures the degree of threat by straights.
   * 
   * @see management.cards.catrecnew.ICatRec#checkStraightDanger()
   */
  @Override
  public StraightDanger checkStraightDanger(List<Card> community) {

    @SuppressWarnings("null")
    Function<Window, Long> countMatchesInWindow = window -> community
        .stream()
        .map(Card::getRank)
        .filter(window::contains)
        .distinct()
        .count();

    @SuppressWarnings("null")
    Map<Long, List<Long>> map2 = Window
        .getDescValues()
        .stream()
        .map(countMatchesInWindow)
        .collect(groupingBy(x -> x));

    @SuppressWarnings("null")
    Map<Long, Integer> map = map2
        .entrySet()
        .stream()
        .collect(
            toMap(entry -> entry.getKey(), entry -> entry
                .getValue().size()));

    int num5 = map.containsKey(5L) ? map.get(5L) : 0;
    int num4 = map.containsKey(4L) ? map.get(4L) : 0;
    int num3 = map.containsKey(3L) ? map.get(3L) : 0;
    int num2 = map.containsKey(2L) ? map.get(2L) : 0;

    if (num5 > 0) {
      return StraightDanger.CERTAIN_STRAIGHT;
    } else if (num4 > 0) {
      return StraightDanger.VERY_HIGH;
    } else if (num3 > 0) {
      switch (num3) {
      case 4:
        return StraightDanger.VERY_HIGH;
      case 3:
        return StraightDanger.HIGH;
      case 2:
        return StraightDanger.SIGNIFICANT;
      case 1:
        return StraightDanger.MODERATE;
      default:
        throw new IllegalStateException("should never happen");
      }
    } else if (num2 > 2 && num2 < 5) {
      return StraightDanger.POSSIBLE_DRAW;
    } else if (num2 > 4) {
      return StraightDanger.PROBABLE_DRAW;
    } else {
      return StraightDanger.NONE;
    }
  }

  @Override
  @SuppressWarnings("null")
  public FlushDanger checkFlushDanger(List<Card> community) {
    if (community.isEmpty()) {
      return FlushDanger.NONE;
    }
    Function<Suit, Long> countMatchesInSuit = suit -> community.stream()
        .map(Card::getSuit)
        .filter(suit::equals)
        .count();

    return Suit.VALUES.stream()
        .map(countMatchesInSuit)
        .max(Comparator.naturalOrder())
        .map(FlushDanger::fromLong)
        .orElse(FlushDanger.NONE);
  }

  @Override
  public DrawType checkDraw(List<Card> all) {
    if (checkDoubleGutshot2(all)) {
      if (checkFlushDraw(all)) {
        return DrawType.MONSTER_DRAW;
      } else {
        return DrawType.DOUBLE_GUTSHOT;
      }
    } else if (checkOESD(all)) {
      if (checkFlushDraw(all)) {
        return DrawType.MONSTER_DRAW;
      } else {
        return DrawType.OESD;
      }
    } else if (checkFlushDraw(all)) {
      return DrawType.FLUSH_DRAW;
    } else if (checkGutshot(all)) {
      return DrawType.GUTSHOT;
    } else {
      return DrawType.NONE;
    }
  }

  // public static ResultImpl check(List<Card> list) {
  // return new Cat_Rec(list.subList(0, 2), list.subList(2, list.size()))
  // .check();
  // }

}
