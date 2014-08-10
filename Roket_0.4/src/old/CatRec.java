package old;


public class CatRec {

	// public Result checkColor(OpenCards cards, int target) {
	// MapOfInteger<CardColor> map = new MapOfInteger<CardColor>();
	// for (Card card : cards.allCards) {
	// map.inc(card.color);
	// }
	// CardColor color = map.argMax();
	// int ownCombo = 0;
	// if (color.equals(cards.hand.first.color))
	// ownCombo++;
	// if (color.equals(cards.hand.second.color))
	// ownCombo++;
	// return new Result(map.max() >= target, ownCombo);
	// }
	//
	// public Result checkFlush(OpenCards cards) {
	// Result r = checkColor(cards, 5);
	// return r;
	// }
	//
	// public Result checkStraightFlush(OpenCards cards) {
	// Result[] r = new Result[4];
	// r[0] = checkStraightColor(cards, CardColor.Clubs);
	// r[1] = checkStraightColor(cards, CardColor.Diamonds);
	// r[2] = checkStraightColor(cards, CardColor.Hearts);
	// r[3] = checkStraightColor(cards, CardColor.Spades);
	// if (r[0].val || r[1].val || r[2].val || r[3].val)
	// return new Result(true, 0);
	// else
	// return new Result(false, 0);
	// }
	//
	// public Result checkStraightColor(OpenCards cards, CardColor color) {
	// int num = 0;
	//
	// for (Card c : cards.allCards) {
	// if (c.color == color && c.number == CardNum.Ace) {
	// num = 1;
	// }
	// }
	//
	// for (CardNum nr : CardNum.values()) {
	// boolean found = false;
	// for (Card c : cards.allCards) {
	// if (c.number == nr && c.color == color) {
	// num++;
	// if (num == 5) {
	// return new Result(true, 0);
	// }
	// }
	// }
	// if (!found) {
	// num = 0;
	// }
	// }
	// return new Result(false, 0);
	// }
	//
	// public Result checkStraight(OpenCards cards) {
	// int num = 0;
	// // int ownCombo = 0;
	//
	// for (Card c : cards.allCards) {
	// if (c.number == CardNum.Ace) {
	// num = 1;
	// }
	// }
	//
	// for (CardNum nr : CardNum.values()) {
	// boolean found = false;
	// for (Card c : cards.allCards) {
	// if (c.number == nr) {
	// num++;
	// if (num == 5) {
	// return new Result(true, 0);
	// }
	// }
	// }
	// if (!found) {
	// num = 0;
	// }
	// }
	// return new Result(false, 0);
	// }
	//
	// public int computeOwnCombo(OpenCards cards, CardNum k, int ownCombo,
	// boolean isOwn) {
	// // CardNum first = cards.hand.first.number;
	// throw new UnsupportedOperationException("not implemented yet");
	// }

}
