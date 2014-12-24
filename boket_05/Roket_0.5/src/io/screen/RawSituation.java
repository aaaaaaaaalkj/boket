package io.screen;

import java.util.ArrayList;
import java.util.List;

import management.cards.cards.Card;

import org.eclipse.jdt.annotation.NonNull;

import tools.Tools;

public class RawSituation {
  public static final int NUM_SEATS = 9;
  public static final double BIG_BLIND = 0.02;
  private final int checkSum;
  private final boolean itsMyTurn;
  private final boolean[] brownButtons;

  private final List<Card> hand;
  private final List<@NonNull Card> communityCards;

  private final boolean[] activeStatus;
  private final double[] posts;
  private final double[] stacks;

  private final int button;
  private final double pot;

  @SuppressWarnings("null")
  public String toString2() {
    StringBuilder b = new StringBuilder();
    b.append("Raw_Situation sit;");

    b.append("sit = new Raw_Situation.Builder()");
    if (hand.size() > 0) {
      b.append(".hand(" +
          hand.get(0).shortString2() + ", " +
          hand.get(1).shortString2() +
          ")");
    } else {
      b.append(".hand()");
    }
    b.append(".communityCards(");
    for (int i = 0; i < communityCards.size(); i++) {
      Card c = communityCards.get(i);
      b.append(c.shortString2());
      if (i < communityCards.size() - 1) {
        b.append(", ");
      }
    }
    b.append(")");
    b.append(".activeStatus(");
    for (int i = 0; i < activeStatus.length; i++) {
      b.append(activeStatus[i]);
      if (i < activeStatus.length - 1) {
        b.append(", ");
      }
    }
    b.append(")");
    b.append(".posts(");
    for (int i = 0; i < posts.length; i++) {
      b.append(posts[i]);
      if (i < posts.length - 1) {
        b.append(", ");
      }
    }
    b.append(")");
    b.append(".stacks(");
    for (int i = 0; i < stacks.length; i++) {
      b.append(stacks[i]);
      if (i < stacks.length - 1) {
        b.append(", ");
      }
    }
    b.append(")");
    b.append(".button(" + button + ")");
    b.append(".pot(" + pot + ")");
    b.append(".itsMyTurn(" + itsMyTurn + ")");
    b.append(".brownButtons(");
    for (int i = 0; i < brownButtons.length; i++) {
      b.append(brownButtons[i]);
      if (i < brownButtons.length - 1) {
        b.append(", ");
      }
    }
    b.append(")");
    b.append(".build();");

    return b.toString();
  }

  RawSituation(List<Card> hand,
      List<@NonNull Card> communityCards, int checkSum,
      boolean[] activeStatus, double[] posts,
      double[] stacks, int button, double pot,
      boolean itsMyTurn, boolean[] brownButtons) {
    super();
    this.hand = hand;
    this.communityCards = communityCards;
    this.checkSum = checkSum;
    this.activeStatus = activeStatus;
    this.posts = posts;
    this.stacks = stacks;
    this.button = button;
    this.pot = pot;
    this.itsMyTurn = itsMyTurn;
    this.brownButtons = brownButtons;
  }

  private int countActive() {
    int count = 0;
    for (boolean active : activeStatus) {
      if (active)
        count++;
    }
    return count;
  }

  @Override
  public String toString() {
    return "[" + (itsMyTurn ? "its my turn," : "")
        + " cards = " + hand + " __ " + communityCards
        + ", " + countActive() + " active players "
        + " pot = " + pot
        // "\n posts = " + Arrays.toString(posts)
        // + "\n stacks = " + Arrays.toString(stacks)
        // +"\n button = " + button
        + "]";
  }

  public static int getNumSeats() {
    return NUM_SEATS;
  }

  public static double getBigBlind() {
    return BIG_BLIND;
  }

  public List<Card> getHand() {
    return hand;
  }

  public List<Card> getCommunityCards() {
    return communityCards;
  }

  public int getCheckSum() {
    return checkSum;
  }

  public int getNumActive() {
    int count = 0;
    for (boolean b : activeStatus) {
      if (b) {
        count++;
      }
    }
    return count;
  }

  public boolean[] getActiveStatus() {
    return activeStatus;
  }

  public double[] getPosts() {
    return posts;
  }

  public int getButton() {
    return button;
  }

  public double getPot() {
    return pot;
  }

  public boolean isItsMyTurn() {
    return itsMyTurn;
  }

  public double getStack() {
    return stacks[0];
  }

  public void print() {
    System.out.println(this);
  }

  public boolean[] getBrownButtons() {
    return brownButtons;
  }

  public double[] getStacks() {
    return stacks;
  }

  public static class Builder {
    private List<Card> hand;
    private List<Card> communityCards;
    private int checkSum;
    private boolean[] activeStatus;
    private double[] posts;
    private double[] stacks;
    private int button;
    private double pot;
    private boolean itsMyTurn;
    private boolean[] brownButtons;

    @SuppressWarnings("null")
    public Builder() {
      hand = new ArrayList<>();
      communityCards = new ArrayList<>();
      activeStatus = new boolean[NUM_SEATS];
      posts = new double[NUM_SEATS];
      stacks = new double[NUM_SEATS];
      brownButtons = new boolean[NUM_SEATS];
      itsMyTurn = false;
    }

    public Builder hand(List<Card> hand1) {
      this.hand = hand1;
      return this;
    }

    public Builder hand(Card c1, Card c2) {
      this.hand = Tools.asList(c1, c2);
      return this;
    }

    public Builder communityCards() {
      this.communityCards = Tools.asList();
      return this;
    }

    public Builder communityCards(Card c1, Card c2, Card c3) {
      this.communityCards = Tools.asList(c1, c2, c3);
      return this;
    }

    public Builder communityCards(Card c1, Card c2,
        Card c3, Card c4) {
      this.communityCards = Tools.asList(c1, c2, c3, c4);
      return this;
    }

    public Builder communityCards(Card c1, Card c2,
        Card c3, Card c4, Card c5) {
      this.communityCards = Tools.asList(c1, c2, c3, c4, c5);
      return this;
    }

    public Builder communityCards(List<Card> communityCards1) {
      this.communityCards = communityCards1;
      return this;
    }

    public Builder checkSum(int checkSum1) {
      this.checkSum = checkSum1;
      return this;
    }

    public Builder activeStatus(boolean... activeStatus1) {
      this.activeStatus = activeStatus1;
      return this;
    }

    public Builder activeStatus(int... activePositions) {
      this.activeStatus = new boolean @NonNull [NUM_SEATS];
      for (int pos : activePositions) {
        this.activeStatus[pos] = true;
      }
      return this;
    }

    public Builder activeStatus(int index, boolean activeStatus1) {
      this.activeStatus[index] = activeStatus1;
      return this;
    }

    public Builder post(int index, double post) {
      this.posts[index] = post;
      return this;
    }

    public Builder posts(double... posts1) {
      this.posts = posts1;
      return this;
    }

    public Builder posts(int index, double post) {
      this.posts[index] = post;
      return this;
    }

    public Builder stacks(double... stacks1) {
      this.stacks = stacks1;
      return this;
    }

    @SuppressWarnings("null")
    public Builder stacks(int numSeats) {
      this.stacks = new double[numSeats];
      return this;
    }

    public Builder stacksAll(double stack) {
      this.stacks = new double @NonNull [NUM_SEATS];
      for (int i = 0; i < NUM_SEATS; i++) {
        this.stacks[i] = stack;
      }
      return this;
    }

    public Builder stacks(int index, double stack) {
      this.stacks[index] = stack;
      return this;
    }

    public Builder button(int button1) {
      this.button = button1;
      return this;
    }

    public Builder pot(double pot1) {
      this.pot = pot1;
      return this;
    }

    public Builder potAdd(double pot1) {
      this.pot += pot1;
      return this;
    }

    public Builder itsMyTurn(boolean itsMyTurn1) {
      this.itsMyTurn = itsMyTurn1;
      return this;
    }

    public Builder itsMyTurn() {
      this.itsMyTurn = true;
      return this;
    }

    public Builder brownButtonsFirstOnly() {
      this.brownButtons = new boolean @NonNull [] { true, false, false };
      return this;
    }

    public Builder brownButtonsAllShown() {
      this.brownButtons = new boolean @NonNull [] { true, true, true };
      return this;
    }

    public Builder brownButtons(
        @SuppressWarnings("hiding") boolean... brownButtons) {
      this.brownButtons = brownButtons;
      return this;
    }

    public Builder brownButtons(int index, boolean brownButton) {
      this.brownButtons[index] = brownButton;
      return this;
    }

    public RawSituation build() {
      return new RawSituation(hand, communityCards, checkSum,
          activeStatus, posts, stacks, button, pot, itsMyTurn,
          brownButtons);
    }

  }

}
