package pot_odds_strategy;

import static java.util.Comparator.naturalOrder;
import input_output.Raw_Situation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import card_simulation.CardSimulation;

public class PotOddsStrategy {
	private final static double PREFLOP_RISK_AFFINITY = 1;
	private final static double RISK_AFFINITY = 1;
	private final double valueOfSituation;
	private final double toPay;
	private final double[] posts;
	private double odds;
	private final double pot;
	private int round;

	@Override
	public String toString() {
		return "PotOddsStrategy [valueOfSituation=" + valueOfSituation
				+ ", toPay=" + toPay + ", posts=" + Arrays.toString(posts)
				+ ", odds=" + odds + ", pot=" + pot + "]";
	}

	public PotOddsStrategy(Raw_Situation s) {
		int count = 0;
		for (boolean b : s.getActiveStatus()) {
			if (b)
				count++;
		}

		this.posts = s.getPosts().clone();
		toPay = ((double) Math.round((Arrays.stream(s.getPosts()).max()
				.getAsDouble()
				- s.getPosts()[0]) * 100) / 100);

		double pot2 = s.getPot();
		for (int i = 0; i < posts.length; i++) {
			if (posts[i] == 0 && s.getActiveStatus()[i]) {
				pot2 += toPay;
			}
		}
		round = s.getCommunityCards().size();
		if (round > 2) {
			round -= 2;
		}

		pot = pot2;

		List<Double> activeContributors = new ArrayList<>();
		for (int i = 0; i < posts.length; i++) {
			if (s.activeStatus[i]) {
				activeContributors.add(posts[i] / s.getPot());
			}
		}

		odds = new CardSimulation(activeContributors, s.hand,
				s.communityCards)
				.run();

		odds = Math.pow(odds, (round / 3 + 1));

		// double odds2 = 0.75 * odds * odds;

		double v = pot * odds / (1 - odds);

		double aversion = RISK_AFFINITY;

		if (this.round == 0) {
			aversion = PREFLOP_RISK_AFFINITY;
		}
		v = v * aversion;

		valueOfSituation = ((double) Math.round(v * 1000)) / 1000;
	}

	private double computeMinRaise() {
		List<Double> list = new ArrayList<>();
		for (double d : posts) {
			list.add(d);
		}

		double max = list.stream().max(naturalOrder()).orElse(0.);

		while (list.contains(max)) {
			list.remove(max);
		}
		double max2 = list.stream().max(naturalOrder()).orElse(0.);

		double myResult = max - max2;

		if (myResult < .02) {
			return 0.02; // at least a bigblind
		}

		return max - max2;
	}

	public PotOddsDecision decide() {
		if (valueOfSituation < toPay) {
			return PotOddsDecision.fold();
		} else if (valueOfSituation < toPay + computeMinRaise()) {
			return PotOddsDecision.call();
		} else {

			double res = ((double) Math.round((valueOfSituation - toPay) * 100)) / 100;

			return PotOddsDecision.raise(res);
		}
	}
}
