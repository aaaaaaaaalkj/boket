package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import managementCards.CardManagement;
import managementCards.cat_rec_new.IResult;
import managementPaymentsNew.IPayManagement2;
import managementPaymentsNew.PayManagementNew;
import managementPaymentsNew.decisions.Decision;
import managementState.Carousel;
import managementState.IGameState;
import managementState.IStateManagement;
import strategy.ISituation;
import strategy.IStrategy;
import strategy.SituationImpl;
import strategy.TypeOfDecision;

import common.Round;

public class NewSimulation {
	CardManagement cardMandagement;
	IPayManagement2 payManagement;
	IStateManagement stateManagement;
	List<IStrategy> strategies;

	@SuppressWarnings("null")
	private static final Logger log = Logger.getLogger(NewSimulation.class
			.getName());

	public NewSimulation() {
		int numSeats = 5;
		this.strategies = new ArrayList<>();
		this.cardMandagement = new CardManagement(numSeats, new Random());
		this.payManagement = PayManagementNew.newInstance(numSeats, 100);
		this.stateManagement = new Carousel(numSeats);
	}

	public void start() {
		IGameState state;

		while (!(state = stateManagement.step()).gameEnded()) {
			if (state.newRound()) {
				payManagement.collectPostsToSidePots();
				cardMandagement.openCards(state.getRound());
				log.info("== " + state.getRound() + "== "
						+ cardMandagement.getCommunityCards());
			}
			playerAction(state);
		}
		if (state.getRound() == Round.SHOWDOWN) {
			showDown(state);
		} else {
			// current player wins everything
			// int winner = state.getCurrentPlayer();
		}
	}

	public void playerAction(IGameState state) {
		int player = state.getCurrentPlayer();
		ISituation sit = new SituationImpl(cardMandagement, state,
				payManagement);

		TypeOfDecision t = strategies.get(player).decide(sit);

		Decision dec = payManagement.action(player, t);

		// strategies.get(player).payed(player, sit, a);

		log.info(player + " " + t + " (" + cardMandagement.getHand(player)
				+ ")");

		// rewards.saveStat(sit, decision.getAmount());

		// logger.info(decision.getModified() + " (" + player + " " + sit.hand
		// + ")");

		stateManagement.update(dec.getDecisionType());

	}

	public void showDown(IGameState state) {
		log.info("showdown");

		Set<Integer> notFolded = state.getNotFoledePlayers();

		List<IResult> results = cardMandagement.getResults();

		List<Integer> paouts = payManagement.payOut(notFolded, results);

		System.out.println(paouts);
	}

}
