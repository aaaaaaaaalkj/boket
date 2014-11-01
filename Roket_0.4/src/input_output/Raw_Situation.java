package input_output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import managementCards.cards.Card;
import old.Hand;

public class Raw_Situation {
	public static final int NUM_SEATS = 9;
	public static final double BIG_BLIND = 0.02;

	public Hand hand;
	public List<Card> communityCards = new ArrayList<>();

	public int checkSum;
	public boolean[] activeStatus = new boolean[NUM_SEATS];
	public double[] posts = new double[NUM_SEATS];
	public double[] stacks = new double[NUM_SEATS];
	public int button;
	public double pot;
	public boolean itsMyTurn = false;

	@Override
	public String toString() {
		return "Raw_Situation [itsMyTurn=" + itsMyTurn + "\n hand = " + hand
				+ "\n communityCards = " + communityCards + "\n button = "
				+ button
				+ "\n activePlayer = " + Arrays.toString(activeStatus)
				+ "\n pot = " + pot + "\n posts = " + Arrays.toString(posts)
				+ "\n stacks = " + Arrays.toString(stacks)
				+ "\n checkSum = " + checkSum + "]";
	}

	public static int getNumSeats() {
		return NUM_SEATS;
	}

	public static double getBigBlind() {
		return BIG_BLIND;
	}

	public Hand getHand() {
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
