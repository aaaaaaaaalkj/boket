package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import managementcards.CardManagement;
import managementcards.catrecnew.IResult;
import managementpaymentsnewtmp.IPayManagement2;
import managementpaymentsnewtmp.PayManagementNew;
import managementpaymentsnewtmp.decisions.Decision;
import managementstate.Carousel;
import managementstate.IGameState;
import managementstate.IStateManagement;
import strategy.ISituation;
import strategy.IStrategy;
import strategy.SituationImpl;
import strategy.TypeOfDecision;

import common.Round;

public class NewSimulation {
  private CardManagement cardMandagement;
  private IPayManagement2 payManagement;
  private IStateManagement stateManagement;
  private List<IStrategy> strategies;

  @SuppressWarnings("null")
  private static final Logger LOG = Logger.getLogger(NewSimulation.class
      .getName());

  public NewSimulation(int numSeats, int stackSize) {
    this.strategies = new ArrayList<>();
    this.cardMandagement = new CardManagement(numSeats, new Random());
    this.payManagement = PayManagementNew.newInstance(numSeats, stackSize);
    this.stateManagement = new Carousel(numSeats);
  }

  public final void start() {
    IGameState state;

    for (state = stateManagement.step(); !state.gameEnded(); state = stateManagement.step()) {
      if (state.newRound()) {
        payManagement.collectPostsToSidePots();
        cardMandagement.openCards(state.getRound());
        LOG.info("== " + state.getRound() + "== "
            + cardMandagement.getCommunityCards());
      }
      playerAction(state);
    }
    if (state.getRound() == Round.SHOWDOWN) {
      showDown(state);
    } else {
      // current player wins everything
      int winner = state.getCurrentPlayer();
      System.out.println(winner);
    }
  }

  public final void playerAction(final IGameState state) {
    int player = state.getCurrentPlayer();
    ISituation sit = new SituationImpl(cardMandagement, state,
        payManagement);

    TypeOfDecision t = strategies.get(player).decide(sit);

    Decision dec = payManagement.action(player, t);

    // strategies.get(player).payed(player, sit, a);

    LOG.info(player + " " + t + " (" + cardMandagement.getHand(player)
        + ")");

    // rewards.saveStat(sit, decision.getAmount());

    // logger.info(decision.getModified() + " (" + player + " " + sit.hand
    // + ")");

    stateManagement.update(dec.getDecisionType());

  }

  public final void showDown(final IGameState state) {
    LOG.info("showdown");

    Set<Integer> notFolded = state.getNotFoledePlayers();

    List<IResult> results = cardMandagement.getResults();

    List<Integer> paouts = payManagement.payOut(notFolded, results);

    System.out.println(paouts);
  }

}
