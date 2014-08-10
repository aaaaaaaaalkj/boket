package input_output;

import java.util.Arrays;

import managementCards.cards.CommunityCards;
import old.Hand;

public class Raw_Situation {
	public static final int NUM_SEATS = 9;
	public static final double BIG_BLIND = 0.02;

	public Hand hand;
	public CommunityCards communityCards = CommunityCards.empty();

	public int checkSum;
	public boolean[] activeStatus = new boolean[NUM_SEATS];
	public double[] posts = new double[NUM_SEATS];
	public int button;
	public double pot;
	public boolean itsMyTurn = false;

	@Override
	public String toString() {
		return "Raw_Situation [itsMyTurn=" + itsMyTurn + "\n hand=" + hand
				+ "\n communityCards=" + communityCards + "\n button=" + button
				+ "\n activePlayer=" + Arrays.toString(activeStatus)
				+ "\n pot=" + pot + "\n posts=" + Arrays.toString(posts)
				+ "\n checkSum=" + checkSum + "]";
	}

}
