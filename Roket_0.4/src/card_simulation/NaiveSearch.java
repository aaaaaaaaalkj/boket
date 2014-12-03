package card_simulation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import managementcards.all_cathegories.AllResults;
import managementcards.cards.Card;
import managementcards.cards.Rank;
import managementcards.cards.Suit;
import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.ResultImpl;

import org.eclipse.jdt.annotation.Nullable;

public final class NaiveSearch implements HandGenerator {
  private List<Card> myHand;
  private List<Card> community;
  private List<Card> deck;
  private List<PossibleHand> hands;
  private AllResults allRes;

  private NaiveSearch(final List<Card> myHand, final List<Card> community) {
    this.myHand = myHand;
    this.hands = new ArrayList<>();
    this.community = community;

    try {
      this.allRes = AllResults
          .getInstance("cathegories.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    deck = new ArrayList<>();
    prepareDeck();
    run();
  }

  private static @Nullable NaiveSearch instance;

  public static NaiveSearch getInstance(final List<Card> myHand,
      final List<Card> community) {
    NaiveSearch x = instance;

    if (x == null || !x.myHand.equals(myHand)
        || !x.community.equals(community)) {
      x = new NaiveSearch(myHand, community);
      instance = x;
    } else {
      System.out.println("reusing NativeSearch");
    }
    return x;
  }

  // public List<PossibleHand> getPossibleHands(double min, double max,
  // double count_opponents) {
  // if (hands.size() == 0) {
  // throw new IllegalStateException("no hands available");
  // }
  // int start = (int) (hands.size() * Math.pow(min, 1. / count_opponents));
  // int end = (int) (hands.size() * Math.pow(max, 1. / count_opponents));
  //
  // List<PossibleHand> res = hands.subList(start, end);
  //
  // if (res.size() == 0) {
  // throw new IllegalArgumentException(
  // "no hands found between min " + start + " and max " + end
  // + ". hands.size = " + hands.size());
  // } else {
  // return res;
  // }
  // }

  public PossibleHand getPossibleHand(final int index) {
    if (hands.size() == 0) {
      throw new IllegalStateException("no hands available");
    }
    return hands.get(index);
  }

  private void run() {

    System.out.println("naive search start");

    for (int first = 0; first < deck.size(); first++) {
      for (int second = first + 1; second < deck.size(); second++) {

        Card first_ = deck.get(first);
        Card second_ = deck.get(second);

        List<Card> communityCards = new ArrayList<>();

        double score = 0;

        if (community.size() == 4) { // turn
          for (int i = 0; i < deck.size(); i++) {
            if (i != first && i != second) {
              communityCards.clear();
              communityCards.addAll(community);
              communityCards.add(deck.get(i));

              ResultImpl res = new CatRec(
                  first_, second_, communityCards)
                  .check();

              score += allRes.getScore(res);
            }
          }
          hands.add(new PossibleHand(first_, second_,
              score / 44));
        }
        if (community.size() == 5) { // river
          ResultImpl res = new CatRec(
              first_, second_, community)
              .check();
          score += allRes.getScore(res);
          hands.add(new PossibleHand(first_, second_,
              score / 1));
        }
      }
    }
    hands.sort((a, b) -> a.getScore().compareTo(b.getScore()));
  }

  private void prepareDeck() {
    for (Suit suit : Suit.VALUES) {
      for (Rank rank : Rank.VALUES) {
        Card c = new Card(rank, suit);
        if (myHand.contains(c)
            || community.contains(c)) {
          continue;
        } else {
          deck.add(c);
        }
      }
    }
    deck.sort((a1, a2) -> {
      if (a1.getRank().compareTo(a2.getRank()) > 0) {
        return 1;
      } else if (a1.getRank().compareTo(a2.getRank()) < 0) {
        return -1;
      } else { // a1.rank == a2.rank here
        if (a1.getSuit().compareTo(a2.getSuit()) > 0) {
          return 1;
        } else if (a1.getSuit().compareTo(a2.getSuit()) < 0) {
          return -1;
        } else {
          return 0;
        }
      }
    });
  }

  public int size() {
    return hands.size();
  }

  @Override
  public List<Card> getHand(final int numPlayers, final double contribution, final double stdDev) {
    Random r = new Random();

    double randNumber = (r.nextGaussian() * stdDev)
        + contribution;

    if (randNumber > 1) {
      randNumber = 1 - Math.abs(randNumber) % 1;
    } else {
      randNumber = Math.abs(randNumber) % 1;
    }

    int randIndex = (int) Math.floor(randNumber
        * size());

    return getPossibleHand(randIndex).getHand();
  }
}
