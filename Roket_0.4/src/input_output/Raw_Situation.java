package input_output;

import java.util.ArrayList;
import java.util.List;

import managementCards.cards.Card;
import old.Hand;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Raw_Situation {
	public static final int NUM_SEATS = 9;
	public static final double BIG_BLIND = 0.02;

	public @Nullable Hand hand;
	public List<@NonNull Card> communityCards = new ArrayList<>();

	public int checkSum;
	public boolean[] activeStatus = new boolean[NUM_SEATS];
	public double[] posts = new double[NUM_SEATS];
	public double[] stacks = new double[NUM_SEATS];
	public int button;
	public double pot;
	public boolean itsMyTurn = false;
	public boolean[] brownButtons = new boolean[3];

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

}
