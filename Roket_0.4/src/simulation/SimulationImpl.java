package simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import managementCards.CardManagement;
import managementCards.ICardManagement;
import managementCards.OutcomeImpl;
import managementCards.cards.Card;
import managementCards.cards.Hand;
import managementPayments.PayOuts;
import managementPayments.PaymentManagement;
import managementRewards.RewardsManagement;
import managementState.Carousel;
import managementState.IGameState;
import managementState.IStateManagement;
import start.StrategyDefinitions;
import tools.Tools;

import common.IOutcome;
import common.IPlayer;
import common.PlayerImpl;
import common.Round;

public class SimulationImpl implements ISimulation {
	ICardManagement cardMandagement;
	PaymentManagement payManagement;
	IStateManagement stateManagement;
	RewardsManagement rewards;
	Random rand;
	Round round;
	@SuppressWarnings("null")
	Logger logger = Logger.getGlobal();
	List<IPlayer> players = new ArrayList<>();

	public SimulationImpl(long l) {
		this.round = Round.PREFLOP;
		this.rand = new Random();
		// -544270288983695018 -> SplitPot
		// 7531026355273090958l -> several flush;
		logger.info("seed: " + l);
		rand.setSeed(l);

		rewards = new RewardsManagement();
		payManagement = new PaymentManagement();
		stateManagement = Carousel.normalGameStart(8);
		cardMandagement = new CardManagement(8, rand);

	}

	public void addDefaultPlayer() {
		int i = 0;

		for (String name : Tools.asList("Alex", "Maria",
				"Natascha",
				"Penelope", "Christine", "Lena", "Anna", "Karina")) {
			addPlayer(new PlayerImpl(name, i++, new Hand(Card._3s, Card._7c),
					StrategyDefinitions.s, 100));
		}
	}

	public void addPlayer(IPlayer player) {
		payManagement.register(player);
	}

	public void postBlinds() {
	}

	public Collection<StrategyResult> showDown() {
		logger.info("showdown");

		OutcomeImpl outcome = new OutcomeImpl();

		List<Card> communityCards = cardMandagement.getCommunityCards();

		players.forEach(player ->
				outcome.computeResult(
						player,
						communityCards)
				);

		IOutcome result = outcome;
		PayOuts payOuts = payManagement.payOut(result);

		return payOuts.keySet().stream()
				.map(player -> StrategyResult.create(
						player.getStrategy(),
						payOuts.get(player))
				)
				.collect(Tools.toList());

		// for (IPlayer player : payOuts.keySet()) {
		// logger.info(player + " won " + payOuts.get(player));
		//
		// PlayerResult r = PlayerResult.create(
		// player,
		// result.getResult(player),
		// null,
		// payOuts.get(player));
		// res.add(r);
		//
		// // strategies.get(player).won(player, payOuts.get(player));
		// }
		// return res;
		// for (PlayerId p : loosers) {
		// strategies.get(p).lost(p);
		// }
	}

	public void start() {
		IGameState state;

		while (!(state = stateManagement.step()).gameEnded()) {
			round = state.getRound();
			if (state.newRound()) {
				payManagement.roundEnd();
				cardMandagement.openCards(state.getRound());
				logger.info("== " + state.getRound() + "== "
						+ cardMandagement.getCommunityCards());
			}
			playerAction(state);
		}
	}

	public void playerAction(IGameState state) {
		// IPlayer player = players.get(state.getCurrentPlayer());
		// ISituation sit;
		// PlayerDecision playerDec;
		// StateInfo stateChange;
		// AmountOfJetons a;
		//
		// sit = new SituationImpl(cardMandagement, state, payManagement);
		//
		// TypeOfDecision t = player.getStrategy().decide(sit);
		//
		// playerDec = payManagement.modifyTypeOfDecision(player, t);
		// a = payManagement.computePost(player, t);
		//
		// // strategies.get(player).payed(player, sit, a);
		//
		// logger.info(player + " " + t + " (" + player.getHand()
		// + ")");
		//
		// // rewards.saveStat(sit, decision.getAmount());
		//
		// // logger.info(decision.getModified() + " (" + player + " " +
		// sit.hand
		// // + ")");
		// stateChange = new StateInfo();
		//
		// if (playerDec == PlayerDecision.FOLD) {
		// stateManagement.fold();
		// // strategies.get(player).lost(player);
		// } else {
		// stateChange = payManagement.post(player, a);
		//
		// if (stateChange.isAllIn()) {
		// stateManagement.allin();
		// }
		// if (stateChange.hasRaised()) {
		// stateManagement.raised();
		// }
		// }
	}

	public static void main(String[] args) {
		long l;
		int num = 1;
		for (int i = 0; i < num; i++) {
			if (i % 5 == 0)
				System.out.print(i + " ");
			Random rand = new Random();
			l = rand.nextLong();
			l = -5698969505933893441l;
			// l = 4460683238130048118l;
			SimulationImpl sim = new SimulationImpl(l);
			sim.addDefaultPlayer();
			sim.postBlinds();
			sim.start();
			sim.showDown();
		}
		System.out.println();
		// System.out.println(Test.nutStrategy);
	}

	@Override
	public Collection<StrategyResult> run() {
		// SimulationImpl sim = new SimulationImpl(l);
		addDefaultPlayer();
		postBlinds();
		start();
		return showDown();
	}
}
