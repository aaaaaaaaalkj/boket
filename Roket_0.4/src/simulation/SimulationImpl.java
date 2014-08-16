package simulation;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import managementCards.CardManagement;
import managementPayments.AmountOfJetons;
import managementPayments.PayOuts;
import managementPayments.PaymentManagement;
import managementPayments.StateInfo;
import managementRewards.RewardsManagement;
import managementState.StateManagement;
import start.StrategyDefinitions;
import strategy.ISituation;
import strategy.PlayerDecision;
import strategy.SituationImpl;
import strategy.TypeOfDecision;
import tools.Logger;

import common.IOutcome;
import common.IPlayer;
import common.PlayerImpl;
import common.PlayerResult;
import common.Round;

public class SimulationImpl implements ISimulation {
	CardManagement cardMandagement;
	PaymentManagement payManagement;
	StateManagement stateManagement;
	RewardsManagement rewards;
	Random rand;
	Round round;
	Logger logger;

	public SimulationImpl(long l) {
		logger = new Logger();
		this.round = Round.PREFLOP;
		this.rand = new Random();
		// -544270288983695018 -> SplitPot
		// 7531026355273090958l -> several flush;
		logger.info("seed: " + l);
		rand.setSeed(l);

		rewards = new RewardsManagement();
		payManagement = new PaymentManagement(100);
		stateManagement = new StateManagement();
		cardMandagement = new CardManagement(rand);

	}

	public void addDefaultPlayer() {
		for (String name : new String[] { "Alex", "Maria", "Natascha",
				"Penelope", "Christine", "Lena", "Anna", "Karina" }) {
			addPlayer(new PlayerImpl(name, StrategyDefinitions.s));
		}
	}

	public void addPlayer(IPlayer player) {
		cardMandagement.register(player);
		stateManagement.register(player);
		payManagement.register(player);
	}

	public void postBlinds() {
		stateManagement.sb();
		payManagement.postSB(stateManagement.getCurrent());

		stateManagement.bb();
		payManagement.postBB(stateManagement.getCurrent());

	}

	public void dealCards() {
		cardMandagement.dealCards();
	}

	public Round playOneRound() {
		cardMandagement.openCards(round);
		logger.emptyLine();
		logger.info("== " + round + "== " + cardMandagement.getCommunityCards());
		while (stateManagement.hasNext()) {
			IPlayer p = stateManagement.next();
			playerAction(p);
			stateManagement.playerDone();
		}
		List<IPlayer> inGame = stateManagement.roundEnd();
		payManagement.roundEnd(inGame);
		round = round.next();
		return round;
	}

	public Collection<PlayerResult> showDown() {
		logger.info("showdown");
		IOutcome result = cardMandagement.getOutcome();
		PayOuts payOuts = payManagement.payOut(result);

		return payOuts.keySet().stream()
				.map(player -> PlayerResult.create(
						player,
						result.getResult(player),
						null,
						payOuts.get(player))
				)
				.collect(Collectors.toList());

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
		logger.info(cardMandagement);

		playOneRound(); // Preflop
		playOneRound(); // Flop
		playOneRound(); // Turn
		playOneRound(); // River

		// do {
		// b = playOneRound();
		// } while (b != null);
	}

	public void playerAction(IPlayer player) {
		ISituation sit;
		PlayerDecision playerDec;
		StateInfo stateChange;
		AmountOfJetons a;

		sit = new SituationImpl(cardMandagement, stateManagement, payManagement);

		TypeOfDecision t = player.getStrategy().decide(sit);

		playerDec = payManagement.modifyTypeOfDecision(player, t);
		a = payManagement.computePost(player, t);

		// strategies.get(player).payed(player, sit, a);

		logger.info(player + " " + t + " (" + cardMandagement.getHand(player)
				+ ")");

		// rewards.saveStat(sit, decision.getAmount());

		// logger.info(decision.getModified() + " (" + player + " " + sit.hand
		// + ")");
		stateChange = new StateInfo();

		if (playerDec == PlayerDecision.FOLD) {
			stateManagement.fold(player);
			// strategies.get(player).lost(player);
		} else {
			stateChange = payManagement.post(player, a);

			stateManagement.allIn(stateChange.all_in);
			stateManagement.raised(stateChange.raised);
		}
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
			sim.dealCards();
			sim.start();
			sim.showDown();
		}
		System.out.println();
		// System.out.println(Test.nutStrategy);
	}

	@Override
	public Collection<PlayerResult> run() {
		// SimulationImpl sim = new SimulationImpl(l);
		addDefaultPlayer();
		postBlinds();
		dealCards();
		start();
		return showDown();
	}
}
