package managementCards.cat_rec_old;

import java.util.ArrayList;
import java.util.Set;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import managementCards.cat_rec_new.Cathegory;

import org.eclipse.jdt.annotation.Nullable;

public class Cat_Rec_old {

	public final static int NUM = Rank.values().length;

	public static Result_old getResult(Set<Card> set) {
		// X.assertTrue(set.size() == 7, "set.size: " + set.size());
		@Nullable
		Result_old r, r2;
		r = checkStraightFlush(set);
		if (null != r)
			return r;
		r = checkPairBased(set);

		if (r.cathegory == Cathegory.FULL_HOUSE
				|| r.cathegory == Cathegory.FOUR_OF_A_KIND)
			return r;
		r2 = checkFlush(set);
		if (null != r2)
			return r2;
		r2 = checkStraight(set);
		if (null != r2)
			return r2;
		return r;
	}

	public static @Nullable Result_old checkFlush(Set<Card> set) {
		throw new RuntimeException("not supported");
		// for (Suit color : Suit.values()) {
		// int counter = 0;
		// ArrayList<Integer> tieBreakers = new ArrayList<Integer>();
		// for (Card c : set) {
		// if (c.getSuit() == color) {
		// counter++;
		// tieBreakers.add(c.getRank().ordinal());
		// }
		// }
		// if (counter >= 5) {
		// int[] arr = X.castIntArr(tieBreakers.toArray());
		// Arrays.sort(arr);
		// arr = X.reverse(arr);
		// int[] arr2 = Arrays.copyOfRange(arr, 0, 5);
		// return Result_old.newInstance(Cathegory.Flush, arr2);
		// }
		// }
		// return null;
	}

	public static boolean checkFlushDraw(Set<Card> set) {
		for (Suit color : Suit.values()) {
			int counter = 0;
			for (Card c : set) {
				if (c.getSuit() == color) {
					counter++;
				}
			}
			if (counter == 4)
				return true;
		}
		return false;
	}

	public static @Nullable Result_old checkStraight(Set<Card> set) {
		int counter = 0;
		int high = -1;
		for (int i = NUM - 1; i > -2; i--) {
			boolean b = true;
			for (Card c : set) {
				if (c.getRank().ordinal() == i
						|| (i == -1 && c.getRank().ordinal() == NUM - 1)) {
					counter++;
					if (counter == 1) {
						high = c.getRank().ordinal();
					}
					b = false;
					break;
				}
			}
			if (b)
				counter = 0;
			if (counter == 5)
				return Result_old.newInstance(Cathegory.STRAIGHT, high);
		}
		return null;
	}

	public static boolean checkOESD(Set<Card> set) {
		int counter = 0;
		// byte high = -1;
		for (int i = NUM - 1; i > -2; i--) {
			boolean found = false;
			for (Card c : set) {
				if (c.getRank().ordinal() == i
						|| (i == -1 && c.getRank().ordinal() == NUM - 1)) {
					counter++;
					// if (counter == 1) {
					// high = (byte) c.number.ordinal();
					// }
					found = true;
					break;
				}
			}
			if (!found)
				counter = 0;
			if (counter == 4)
				return true;
			// return Result.newInstance(Cathegory.OESD, high);
		}
		return false;
	}

	public static boolean checkDoubleGutshot(Set<Card> set) {
		int firstGap = -1;

		for (int i = NUM - 1; i > 3; i--) {
			int gap = -1;
			boolean found = true;
			k: for (int j = i; j > -2 && j > i - 5; j--) {
				for (Card c : set) {
					if (c.getRank().ordinal() == j
							|| (j == -1 && c.getRank().ordinal() == NUM - 1)) {
						// ok
					} else {
						if (gap != -1) {
							found = false;
							break k;
						} else {
							gap = j;
						}
					}
				}
			}
			if (found) {
				if (firstGap != -1) {
					return true;
				} else {
					firstGap = gap;
				}
			}
		}
		return false;
	}

	public static boolean checkGutshot(Set<Card> set) {

		for (int i = NUM - 1; i > 3; i--) {
			boolean foundTop = false;
			boolean foundBottom = false;

			for (Card c : set) {
				if (c.getRank().ordinal() == i
						|| (i == -1 && c.getRank().ordinal() == NUM - 1)) {
					foundTop = true;
				}
				if (c.getRank().ordinal() == i - 4
						|| (i - 4 == -1 && c.getRank().ordinal() == NUM - 1)) {
					foundBottom = true;
				}
			}
			if (!foundBottom || !foundTop)
				continue;

			int count = 0;
			for (int j = i; j > i - 5; j--) {
				for (Card c : set) {
					if (c.getRank().ordinal() == j
							|| (j == -1 && c.getRank().ordinal() == NUM - 1)) {
						count++;
					}
				}
			}
			if (count == 4)
				return true;
		}
		return false;
	}

	public static @Nullable Result_old checkStraightFlush(Set<Card> set) {
		int counter = 0;
		int high = -1;
		for (Suit color : Suit.values()) {
			for (int i = NUM - 1; i > -2; i--) {
				boolean b = true;
				for (Card c : set) {
					if (c.getSuit() != color)
						continue;
					if (c.getRank().ordinal() == i
							|| (i == -1 && c.getRank().ordinal() == NUM - 1)) {
						counter++;
						if (counter == 1) {
							high = (byte) c.getRank().ordinal();
						}
						b = false;
						break;
					}
				}
				if (b) {
					counter = 0;
				}
				if (counter == 5)
					return Result_old.newInstance(Cathegory.STRAIGHT_FLUSH,
							high);
			}
		}
		return null;
	}

	private static Result_old checkPairBased(Set<Card> set) {
		byte[] res = new byte[NUM];
		for (Card c : set) {
			res[c.getRank().ordinal()]++;
		}
		Cathegory cat = null;
		int bestI = -1;
		int bestI2 = -1;

		k: for (int i = NUM - 1; i >= 0; i--) {
			switch (res[i]) {
			case 4:
				cat = Cathegory.FOUR_OF_A_KIND;
				bestI = i;
				break k;
			case 3:
				if (cat == Cathegory.PAIR || cat == Cathegory.TWO_PAIR) {
					bestI2 = bestI;
					bestI = i;
					cat = Cathegory.FULL_HOUSE;
					break k;
				}
				cat = Cathegory.THREE_OF_A_KIND;
				bestI = i;
				break;
			case 2:
				if (cat == Cathegory.THREE_OF_A_KIND) {
					bestI2 = i;
					cat = Cathegory.FULL_HOUSE;
					break k;
				}
				if (cat == Cathegory.PAIR) {
					bestI2 = i;
					cat = Cathegory.TWO_PAIR;
					break k;
				}
				cat = Cathegory.PAIR;
				bestI = i;
				break;
			}
		}

		if (cat == Cathegory.FOUR_OF_A_KIND)
			return Result_old.newInstance(cat, bestI);

		if (cat == Cathegory.FULL_HOUSE)
			return Result_old.newInstance(cat, bestI, bestI2);
		if (cat == Cathegory.THREE_OF_A_KIND) {
			int tie1 = -1;
			int tie2 = -1;
			for (int i = NUM - 1; i >= 0; i--) {
				if (i == bestI)
					continue;
				if (res[i] >= 1) {
					if (res[i] == 3) {
						// folgendes macht Sinn, weil es möglich ist zwei
						// Drillinge zu haben. Die Karten des niedrigeren
						// Drillings sind Tie-breakers
						if (tie1 != -1)
							return Result_old.newInstance(cat, bestI, tie1, i);
						else
							return Result_old.newInstance(cat, bestI, i, i);
					}
					if (tie1 == -1) {
						tie1 = i;
					} else {
						tie2 = i;
						return Result_old.newInstance(cat, bestI, tie1, tie2);
					}
				}
			}// TODO: this happens sometimes
			throw new RuntimeException("should never happen2");
		}
		if (cat == Cathegory.TWO_PAIR) {
			for (int i = NUM - 1; i >= 0; i--) {
				if (i == bestI || i == bestI2)
					continue;
				if (res[i] > 0) {
					return Result_old.newInstance(cat, bestI, bestI2, i);
				}
			}
			// only four cards available -> no tie-breakers
			return Result_old.newInstance(cat, bestI, bestI2);
		}
		if (cat == Cathegory.PAIR) {
			ArrayList<Integer> tie = new ArrayList<Integer>();
			for (int i = NUM - 1; i >= 0; i--) {
				if (res[i] == 1) {
					tie.add(i);
				}
			}
			tie.add(0, bestI);
			return Result_old.newInstance(cat, toIntArr(tie));
		}
		if (null == cat) {
			cat = Cathegory.HIGH_CARD;
			ArrayList<Integer> tie = new ArrayList<>();
			for (int i = NUM - 1; i >= 0; i--) {
				if (res[i] == 1) {
					tie.add(i);
				}
			}
			return Result_old.newInstance(cat, toIntArr(tie));
		}
		throw new RuntimeException("should never happen1. cat: " + cat);
	}

	@SuppressWarnings("null")
	private static final  @Nullable  int[] toIntArr(ArrayList<Integer> list) {
		int[] ar = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
			ar[i] = list.get(i);
		return ar;
	}
}
