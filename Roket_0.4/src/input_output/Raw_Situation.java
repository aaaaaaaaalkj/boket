package input_output;

import java.util.ArrayList;
import java.util.List;

import managementCards.cards.Card;
import old.Hand;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

public class Raw_Situation {
	public static final int NUM_SEATS = 9;
	public static final double BIG_BLIND = 0.02;

	private final @Nullable Hand hand;
	private final List<@NonNull Card> communityCards;

	private final int checkSum;
	private final boolean[] activeStatus;
	private final double[] posts;
	private final double[] stacks;
	private final int button;
	private final double pot;
	private final boolean itsMyTurn;
	private final boolean[] brownButtons;

	@SuppressWarnings("null")
	public String toString2() {
		StringBuilder b = new StringBuilder();
		b.append("Raw_Situation sit;");

		b.append("sit = new Raw_Situation.Builder()");
		Hand h = hand;
		if (h != null) {
			b.append(".hand(" +
					h.first.shortString2() + ", " +
					h.second.shortString2() +
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

	private Raw_Situation(@Nullable Hand hand,
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

	public @Nullable Hand getHand() {
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
		private @Nullable Hand hand;
		private List<Card> communityCards;
		private int checkSum;
		private boolean[] activeStatus;
		private double[] posts;
		private double[] stacks;
		private int button;
		private double pot;
		private boolean itsMyTurn;
		private boolean[] brownButtons;

		public Builder() {
			communityCards = new ArrayList<>();
			activeStatus = new boolean @NonNull [NUM_SEATS];
			posts = new double @NonNull [NUM_SEATS];
			stacks = new double @NonNull [NUM_SEATS];
			brownButtons = new boolean @NonNull [NUM_SEATS];
			itsMyTurn = false;
		}

		public Builder hand(@Nullable Hand hand) {
			this.hand = hand;
			return this;
		}

		public Builder hand() {
			this.hand = null;
			return this;
		}

		public Builder hand(Card c1, Card c2) {
			this.hand = Hand.newInstance(c1, c2);
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

		public Builder communityCards(List<Card> communityCards) {
			this.communityCards = communityCards;
			return this;
		}

		public Builder checkSum(int checkSum) {
			this.checkSum = checkSum;
			return this;
		}

		public Builder activeStatus(boolean... activeStatus) {
			this.activeStatus = activeStatus;
			return this;
		}

		public Builder activeStatus(int... activePositions) {
			this.activeStatus = new boolean @NonNull [NUM_SEATS];
			for (int pos : activePositions) {
				this.activeStatus[pos] = true;
			}
			return this;
		}

		public Builder activeStatus(int index, boolean activeStatus) {
			this.activeStatus[index] = activeStatus;
			return this;
		}

		public Builder post(int index, double post) {
			this.posts[index] = post;
			return this;
		}

		public Builder posts(double... posts) {
			this.posts = posts;
			return this;
		}

		public Builder posts(int index, double post) {
			this.posts[index] = post;
			return this;
		}

		public Builder stacks(double... stacks) {
			this.stacks = stacks;
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

		public Builder button(int button) {
			this.button = button;
			return this;
		}

		public Builder pot(double pot) {
			this.pot = pot;
			return this;
		}

		public Builder potAdd(double pot) {
			this.pot += pot;
			return this;
		}

		public Builder itsMyTurn(boolean itsMyTurn) {
			this.itsMyTurn = itsMyTurn;
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

		public Builder brownButtons(boolean... brownButtons) {
			this.brownButtons = brownButtons;
			return this;
		}

		public Builder brownButtons(int index, boolean brownButton) {
			this.brownButtons[index] = brownButton;
			return this;
		}

		public Raw_Situation build() {
			return new Raw_Situation(hand, communityCards, checkSum,
					activeStatus, posts, stacks, button, pot, itsMyTurn,
					brownButtons);
		}

	}

}
