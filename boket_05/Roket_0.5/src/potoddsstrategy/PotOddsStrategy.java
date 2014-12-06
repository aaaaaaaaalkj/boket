package potoddsstrategy;

import static java.util.Comparator.naturalOrder;
import inputoutput.Raw_Situation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import management.cards.cards.Hand;

import org.slf4j.LoggerFactory;

import strenghtanalysis.StrenghtAnalysis;
import tools.Tools;
import cardsimulation.CardSimulation;

public class PotOddsStrategy {
	@SuppressWarnings("null")
	static final org.slf4j.Logger LOG = LoggerFactory
			.getLogger(PotOddsStrategy.class);

	private double valueOfSituation;
	private double toPay;
	private double[] posts;
	private double odds;
	private double pot;
	private int round;
	private double nakedPod;

	// private static Memory memory = new Memory();

	@Override
	public final String toString() {
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

	public PotOddsStrategy(final Raw_Situation s) {
		List<Double> activeContributors;
		this.posts = s.getPosts().clone();

		double maxPost = Arrays.stream(s.getPosts()).max().getAsDouble();
		toPay = Tools.round(maxPost - s.getPosts()[0]);

		round = s.getCommunityCards().size();
		if (round > 2) {
			round -= 2;
		}

		nakedPod = s.getPot();
		for (double d : posts) {
			nakedPod -= d;
		}
		nakedPod = Tools.round(nakedPod);
		double pot2 = nakedPod;
		double[] newPosts = new double[posts.length];
		for (int i = 0; i < posts.length; i++) {
			if (s.getActiveStatus()[i]) {
				newPosts[i] = toPay + posts[0];
				pot2 += newPosts[i];
			}
		}
		pot = Tools.round(pot2);

		int count = 0;
		for (int i = 0; i < posts.length; i++) {
			if (s.getActiveStatus()[i]) {
				count++;
			}
		}

		StrenghtAnalysis anal = new StrenghtAnalysis(s);
		activeContributors = anal.getStrength();
		List<Double> stdDev = anal.getStdDev();

		Hand hand = s.getHand();
		if (hand == null) {
			return;
		}

		LOG.debug("contributions: {}", activeContributors);
		LOG.debug("stdDev: {}", stdDev);

		odds = new CardSimulation(count, activeContributors, stdDev,
				hand,
				s.getCommunityCards())
				.run();

		// Andreas möchte hier eine 2 statt 1 haben
		double value = Math.min(Integer.MAX_VALUE, pot * odds / (1 - odds));

		valueOfSituation = Tools.round(value);
	}

	private static final double BIG_BLIND = .02;

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

		if (myResult < BIG_BLIND) {
			return BIG_BLIND; // at least a bigblind
		}

		return max - max2;
	}

	private static final int MAX_ALLOWED_BET = 100000;

	public final PotOddsDecision decide() {
		if (valueOfSituation < toPay) {
			return PotOddsDecision.fold();
		} else if (valueOfSituation < toPay + computeMinRaise()) {
			return PotOddsDecision.call(toPay);
		} else {

			double res = Tools.round(valueOfSituation - toPay);

			res = Math.min(MAX_ALLOWED_BET, res);

			return PotOddsDecision.raise(res);
		}
	}
}
