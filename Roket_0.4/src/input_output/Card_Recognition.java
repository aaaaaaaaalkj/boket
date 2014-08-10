package input_output;

import static input_output.MyRobot.getPixelColor;
import static input_output.MyRobot.pixelSearch;
import static tools.Input_Tool.pat;
import static tools.Input_Tool.pos;
import static tools.Input_Tool.toRGB;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import managementCards.cards.Card;
import managementCards.cards.CommunityCards;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import old.Hand;
import tools.Pattern;
import tools.Pos;

public class Card_Recognition {
	private static final Color cardEdge = Constants.cardEdge;
	private static final Pos handPos = Constants.centersOfPlayerCirles[0];
	private static final int radius = Constants.radiusOfPlayerCircles;

	private static Pos searchCard(Pos pos) {
		Pos hit = pixelSearch(pos.x, pos.y, 25, 25, cardEdge);
		if (null == hit) {
			return null;
		}
		hit = hit.plus(pos).minus(4, 0);
		return hit;
	}

	public static CommunityCards recognizeCommunityCards(Pos logo) {

		Pos pos = Constants.flop.plus(logo);
		List<Card> list = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			Pos hit = searchCard(pos);
			if (null == hit)
				break;
			list.add(recognizeCard(hit));
			pos = hit.plus(Constants.cardOffset, 0);
		}
		// TODO: maybe its possible that list.get(0) == null
		CommunityCards cards = CommunityCards.fromList(list);

		return cards;
	}

	public static Hand recognizeHand(Pos logo) {
		Pos pos = logo.plus(handPos).minus(radius, radius);

		Pos hit = searchCard(pos);
		if (null == hit)
			return null;
		Card first = recognizeCard(hit);

		pos = hit.plus(4, 1);
		hit = searchCard(pos);
		if (null == hit)
			return null;
		Card second = recognizeCard(hit);
		return Hand.newInstance(first, second);
	}

	public static Card recognizeCard(Pos pos) {
		Suit resColor = recognizeCardColor(pos);
		if (null == resColor)
			return null;
		Rank resNum = recognizeCardNum(pos);
		if (resNum == null)
			return null;
		return Card.newInstance(resNum, resColor);
	}

	private static Rank recognizeCardNum(Pos pos) {
		final int num = 6;
		Pos[] positions = { pos(7, 11), pos(9, 14), pos(4, 11), pos(7, 5),
				pos(6, 12), pos(11, 8) };
		Pattern myPattern = new Pattern(num);

		for (int i = 0; i < num; i++) {
			Color c = getPixelColor(positions[i].plus(pos));
			Color ref = new Color(16777215);
			if (c.equals(ref))
				myPattern.set(i);
		}

		Pattern[] pattern = { pat(1, 1, 1, 0, 1, 0), pat(0, 1, 1, 0, 1, 0),
				pat(1, 0, 0, 1, 0, 1), pat(0, 1, 0, 0, 1, 1),
				pat(0, 1, 0, 0, 0, 1), pat(1, 0, 1, 0, 1, 0),
				pat(0, 1, 1, 0, 0, 0), pat(1, 1, 0, 0, 0, 0),
				pat(0, 1, 0, 1, 1, 0), pat(1, 0, 1, 1, 1, 0),
				pat(1, 0, 0, 0, 0, 0), pat(0, 0, 1, 0, 0, 0),
				pat(0, 1, 1, 0, 0, 1) };
		Rank resNum = null;
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i].equals(myPattern)) {
				resNum = Rank.values()[i];
			}
		}
		if (resNum == null)
			System.out.println("cant recognize card" + myPattern);
		return resNum;
	}

	private static Suit recognizeCardColor(Pos pos) {
		Suit resColor;
		Color cardColor = getPixelColor(pos.plus(9, 25));

		switch (toRGB(cardColor)) {
		case 13109256:
			resColor = Suit.Diamonds;
			break;
		case 13174792:
			resColor = Suit.Hearts;
			break;
		case 0:
			resColor = Suit.Spades;
			break;
		case 16448250:
			resColor = Suit.Clubs;
			break;
		default:
			// Logger.warning("unexpected Color in recognizeColor():"
			// + toRGB(cardColor));
			resColor = null;
		}
		return resColor;
	}

}
