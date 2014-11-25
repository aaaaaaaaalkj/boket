package pot_odds_strategy;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;
import input_output.Raw_Situation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import old.Hand;

import org.slf4j.LoggerFactory;

import strenght_analysis.StrenghtAnalysis;
import card_simulation.CardSimulation;

public class PotOddsStrategy {
	@SuppressWarnings("null")
	final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(PotOddsStrategy.class);

	private double valueOfSituation;
	private double toPay;
	private double[] posts;
	private double odds;
	private double pot;
	private int round;
	double prod_der_gegenwahrscheinlichkeiten_meiner_aktiven_gegner = 1;
	double gegenwahrscheinlichkeit_von_zufaelligen_gegnern_mit_zufaelligen_haenden;
	private double naked_pod;

	// private static Memory memory = new Memory();

	@Override
	public String toString() {
		return "[value Of Situation = " + valueOfSituation
				+ ", toPay=" + toPay
				+ ", pot=" + pot
				+ ", odds=" + odds
				// "\nprod der gegen-wahrscheinlichkeit: "
				// + prod_der_gegenwahrscheinlichkeiten_meiner_aktiven_gegner
				// +
				// "\ngegenwahrscheinlichkeit_von_zufälligen_gegnern: "
				// +
				// gegenwahrscheinlichkeit_von_zufaelligen_gegnern_mit_zufaelligen_haenden
				+ "]";
	}

	public PotOddsStrategy(Raw_Situation s) {

		this.posts = s.getPosts().clone();

		double maxPost = Arrays.stream(s.getPosts()).max().getAsDouble();
		toPay = ((double) Math.round((maxPost - s.getPosts()[0]) * 100) / 100);

		round = s.getCommunityCards().size();
		if (round > 2) {
			round -= 2;
		}

		naked_pod = s.getPot();
		for (double d : posts) {
			naked_pod -= d;
		}
		naked_pod = ((double) Math.round(naked_pod * 100)) / 100;
		double pot2 = naked_pod;
		double[] new_posts = new double[posts.length];
		for (int i = 0; i < posts.length; i++) {
			if (s.getActiveStatus()[i]) {
				new_posts[i] = toPay + posts[0];
				pot2 += new_posts[i];
			}
		}
		pot = ((double) Math.round(pot2 * 100)) / 100;

		int count = 0;
		for (int i = 0; i < posts.length; i++) {
			if (s.getActiveStatus()[i]) {
				count++;
			}
		}

		List<Double> activeContributors = new ArrayList<>();
		for (int i = 1; i < posts.length; i++) {
			if (s.getActiveStatus()[i]) {
				if (i <= s.getButton() && new_posts[i] == 0) {
					activeContributors.add(naked_pod / count
							/ pot);
				} else {
					activeContributors.add(posts[i] / s.getPot());
				}
			}
		}

		for (int i = 0; i < activeContributors.size(); i++) {
			activeContributors
					.set(i,
							((double) Math.round(activeContributors.get(i) * 100)) / 100);
		}

		activeContributors = new StrenghtAnalysis(s).getStrength();

		Hand hand = s.getHand();
		if (hand == null) {
			return;
		}

		logger.debug("contributions: {}"
				, activeContributors.stream().map(String::valueOf)
						.collect(joining(", ")));
		odds = new CardSimulation(count, activeContributors, hand,
				s.getCommunityCards())
				.run();

		// Andreas möchte hier eine 2 statt 1 haben
		double value = Math.min(Integer.MAX_VALUE, pot * odds / (1 - odds));

		// * computeAndreasFaktor(countActivePlayers, s.button,
		// s.activeStatus)

		valueOfSituation = ((double) Math.round(value * 100)) / 100;
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
			return PotOddsDecision.call(toPay);
		} else {

			double res = ((double) Math.round((valueOfSituation - toPay) * 100)) / 100;

			res = Math.min(100000, res);

			return PotOddsDecision.raise(res);
		}
	}
}
