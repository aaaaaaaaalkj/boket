package tools;

import java.util.List;

import managementcards.all_cathegories.AllResults;
import managementcards.cards.Card;
import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.ResultImpl;

import org.eclipse.jdt.annotation.NonNull;

public class BinomCoeff {
	// binomial coefficients
	private final int[][] C;
	private final int N;
	private final int K;

  @SuppressWarnings("null")
  public BinomCoeff(int n, int k) {
		N = n;
		K = k;
    C = new int[N + 1][K + 1];

		for (int k_ = 1; k_ <= K; k_++) {
			C[0][k_] = 0;
		}
		for (int n_ = 0; n_ <= N; n_++)
			C[n_][0] = 1;

		for (int n_ = 1; n_ <= N; n_++)
			for (int k_ = 1; k_ <= K; k_++)
				C[n_][k_] = C[n_ - 1][k_ - 1] + C[n_ - 1][k_];

	}

  @SuppressWarnings("null")
  public int hash(List<Card> cards) {// overloaded version for convinience
    int[] ar = new int[cards.size()];
		int i = 0;
		for (Card c : cards) {
			ar[i++] = c.ordinal();
		}
		return hash(ar);
	}

	// bino[n][k] is (n "over" k) = C(n,k) = {n \choose k}
	// these are assumed to be precomputed globals

	public int hash(int... a) {// V is assumed to be
		// ordered,
		// a_k<...<a_1
		// hash(a_k,..,a_2,a_1) = (n k) - sum_(i=1)^k (n-a_i i)
		// ii is "inverse i", runs from left to right

		assert a.length == K : "parameter must be of size " + K;

		int res = C[N][K]; // 133784560

		for (int i = 0; i < K; i++) {
			res = res - C[N - a[i]][K - i];
		}
		return res;
	}

	// public static void main(String[] args) {
	// List<Integer> tragerMenge = Arrays
	// .asList(1, 2, 3, 4, 5);
	// int n = tragerMenge.size();
	// BinomCoeff coeff = new BinomCoeff(n, 2);
	//
	// for (int i1 = 0; i1 < n; i1++) {
	// for (int i2 = i1 + 1; i2 < n; i2++) {
	// int hash = coeff.hash(tragerMenge.get(i1),
	// tragerMenge.get(i2));
	// System.out.println(tragerMenge.get(i1) + ", " +
	// tragerMenge.get(i2) + " -> " + hash);
	// }
	// }
	//
	// }

	public static short[] createAll7Cards(AllResults allRes) {

		List<Card> allCards = Card.getAllCards();
		int n = allCards.size();

		// long[] prim = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
		// 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107,
		// 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
		// 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239 };

		// Map<Long, Integer> res2 = new HashMap<>();
		// TLongIntHashMap map = new TLongIntHashMap();
		short @NonNull [] intAr = new short @NonNull [133784560];

		BinomCoeff coeff = new BinomCoeff(52, 7);

		long start = System.currentTimeMillis();
		int i = 0;
		for (int i1 = 0; i1 < n; i1++) {
			for (int i2 = i1 + 1; i2 < n; i2++) {
				for (int i3 = i2 + 1; i3 < n; i3++) {
					for (int i4 = i3 + 1; i4 < n; i4++) {
						for (int i5 = i4 + 1; i5 < n; i5++) {
							for (int i6 = i5 + 1; i6 < n; i6++) {
								for (int i7 = i6 + 1; i7 < n; i7++) {
									Card c1 = allCards.get(i1);
									Card c2 = allCards.get(i2);
									Card c3 = allCards.get(i3);
									Card c4 = allCards.get(i4);
									Card c5 = allCards.get(i5);
									Card c6 = allCards.get(i6);
									Card c7 = allCards.get(i7);

									// long perfectHash =
									// prim[c1.ordinal()]
									// * prim[c2.ordinal()]
									// * prim[c3.ordinal()]
									// * prim[c4.ordinal()]
									// * prim[c5.ordinal()]
									// * prim[c6.ordinal()]
									// * prim[c7.ordinal()];

									int hash = coeff.hash(
											c1.ordinal(),
											c2.ordinal(),
											c3.ordinal(),
											c4.ordinal(),
											c5.ordinal(),
											c6.ordinal(),
											c7.ordinal()
											);

									ResultImpl res = new CatRec(
											c1, c2, Tools.asList(c3, c4, c5,
													c6, c7))
											.check();
									short score = allRes.getScore(res);

									// System.out.println(
									// c1.ordinal() + " " +
									// c2.ordinal() + " " +
									// c3.ordinal() + " " +
									// c4.ordinal() + " " +
									// c5.ordinal() + " " +
									// c6.ordinal() + " " +
									// c7.ordinal() + " -> " +
									// hash + " -> " +
									// score
									//
									// );

									intAr[hash - 1] = score;

									// if (i % 100 == 99)
									// System.exit(1);

									if (i++ % 1000000 == 0) {
										long l = System.currentTimeMillis()
												- start;
										System.out.println("millis: " + l);
									}
								}
							}
						}
					}
				}
			}
		}
		long l = System.currentTimeMillis() - start;
		System.out.println("overall-millis: " + l);
		return intAr;
	}
}
