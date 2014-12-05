package managementpaymentstmp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import managementpaymentsnewtmp.IPayManagement;

import org.apache.commons.lang3.NotImplementedException;

import strategy.PlayerDecision;
import strategy.TypeOfDecision;

import common.IOutcome;
import common.IPlayer;

public class PaymentManagement implements IPayManagement {
  private AmountOfJetons highestBid;
  private AmountOfJetons lastRaise;
  private List<SidePot> pots;
  private final List<IPlayer> players;

  public PaymentManagement() {
    this.highestBid = AmountOfJetons.ZERO;
    this.pots = new ArrayList<>();
    this.lastRaise = AmountOfJetons.BB;
    this.players = new ArrayList<>();
  }

  public final AmountOfJetons getStack(final IPlayer player) {
    return player.getStack().get();
  }

  public final void postSB(final IPlayer player) {
    postIntern(player, AmountOfJetons.SB);
    lastRaise = AmountOfJetons.BB;
    highestBid = AmountOfJetons.BB;
  }

  public final void postBB(final IPlayer player) {
    postIntern(player, AmountOfJetons.BB);
    lastRaise = AmountOfJetons.BB;
    highestBid = AmountOfJetons.BB;
  }

  public final void post(final IPlayer player, final AmountOfJetons amount) {
    postIntern(player, modifyAmount(player, amount));
  }

  private boolean postIntern(final IPlayer player, final AmountOfJetons amount) {
    AmountOfJetons wholeAmount = amount.plus(player.getPost());
    boolean raised = wholeAmount.greaterAs(highestBid);
    if (raised) {
      lastRaise = wholeAmount.minus(highestBid);
      highestBid = wholeAmount;
    }
    player.getStack().removeFromStack(amount);
    player.setPost(player.getPost().plus(amount));
    return raised;
  }

  public final PlayerDecision modifyTypeOfDecision(final IPlayer player, final TypeOfDecision d) {
    if (d == TypeOfDecision.CALL) {
      if (highestBid.minus(player.getPost()).isZero()) {
        return PlayerDecision.CHECK;
      }
    }
    if (d == TypeOfDecision.FOLD) {
      return PlayerDecision.FOLD;
    }
    if (d == TypeOfDecision.CALL) {
      return PlayerDecision.CALL;
    }
    assert d != TypeOfDecision.FOLD && d != TypeOfDecision.CALL;
    // Only Raise-Types possible here

    if (highestBid.isZero()) {
      return PlayerDecision.BET;
    }

    if (highestBid.minus(player.getPost()).greaterOrEqual(getStack(player))) {
      return PlayerDecision.CALL;
    }
    // if (lastRaise.greaterAs(AmountOfJetons.BB))
    // return PlayerDecision.RERAISE;
    return PlayerDecision.RAISE;
  }

  public final AmountOfJetons computePost(final IPlayer player, final TypeOfDecision d) {
    AmountOfJetons amount = AmountOfJetons.ZERO;
    AmountOfJetons wholeAmount;

    // at least we need to call the highest bid
    amount = highestBid.minus(player.getPost());

    AmountOfJetons stack = player.getStack().get();

    switch (d) {
    case ALL_IN:
      amount = stack;
      break;
    case CALL:
      // amount already computed
      break;
    case FOLD:
      amount = AmountOfJetons.ZERO;
      break;
    case RAISE_DOUBLE_POT:
      amount = amount.plus(computeTotalPot(player).times(2));
      break;
    case RAISE_HALF_POT:
      amount = amount.plus(computeTotalPot(player).divideToEven(2));
      break;
    case RAISE_POT_SIZE:
      amount = amount.plus(computeTotalPot(player));
      break;
    case RAISE_QUARTER_POT:
      amount = amount.plus(computeTotalPot(player).divideToEven(4));
      break;
    case RAISE_FIFTH_STACK:
      amount = amount.plus(stack.divideToEven(5));
      break;
    case RAISE_HALF_STACK:
      amount = amount.plus(stack.divideToEven(2));
      break;
    case RAISE_TENTH_STACK:
      amount = amount.plus(stack.divideToEven(10));
      break;
    default:
      throw new UnsupportedOperationException(d + " is not supported");
    }

    // cant put more jetons on table than available
    amount = AmountOfJetons.min(amount, stack);

    wholeAmount = amount.plus(player.getPost());

    Post post = new Post(stack, player.getPost(), highestBid, lastRaise,
        amount);

    if (post.moreThanCall()) {
      if (post.lessThanRaise() && post.notAllIn()) {
        amount = highestBid.plus(lastRaise);
        // d = TypeOfDecision.RAISE_QUARTER_POT;
      }
      if (!wholeAmount.minus(highestBid).isEven()) {
        amount = wholeAmount.plus(AmountOfJetons.SB);
      }
    }
    amount = AmountOfJetons.min(amount, stack);
    return amount;
  }

  private AmountOfJetons modifyAmount(final IPlayer player, final AmountOfJetons amount) {
    AmountOfJetons wholeAmount = amount.plus(player.getPost());
    AmountOfJetons stack = player.getStack().get();

    AmountOfJetons amount1 = amount;

    Post post = new Post(stack, player.getPost(), highestBid, lastRaise,
        amount);

    if (post.lessThanHighestBid() && post.notAllIn()) {
      amount1 = highestBid;
    }
    if (post.moreThanCall()) {
      if (post.lessThanRaise() && post.notAllIn()) {
        amount1 = highestBid.plus(lastRaise);
      }
      if (!wholeAmount.minus(highestBid).isEven()) {
        amount1 = wholeAmount.plus(AmountOfJetons.SB);
      }
    }
    amount1 = AmountOfJetons.min(amount, stack);
    return amount1;
  }

  /**
   * a round is over. everybody should have contributed the same amount to the pot (except all-in's)
   * Otherwise exit() should be called.
   * 
   * @param inGame
   */
  public final void roundEnd() {
    AmountOfJetons minPost;
    AmountOfJetons removed;

    for (minPost = minPost(); minPost.positive(); minPost = minPost()) {
      SidePot pot = new SidePot();
      for (IPlayer p : players) {
        removed = p.getPost().min(minPost);
        p.setPost(p.getPost().minus(removed));

        if (p.getState().isInGame()) {
          pot.add(p, removed); // all-in or active
        } else {
          pot.add(removed); // folded
        }
      }
      pots.add(pot);
    }
    highestBid = AmountOfJetons.ZERO;
    lastRaise = AmountOfJetons.BB;
  }

  private AmountOfJetons minPost() {
    AmountOfJetons res = AmountOfJetons.INFINITY;
    for (IPlayer p : players) {
      if (p.getStack().isEmpty() && p.getPost().positive()) {
        res = AmountOfJetons.min(res, p.getPost());
      }
    }
    if (res == AmountOfJetons.INFINITY) {
      res = AmountOfJetons.ZERO;
      for (IPlayer p : players) {
        res = AmountOfJetons.max(res, p.getPost());
      }
    }
    return res;
  }

  public final void register(final IPlayer player) {
    player.setPost(AmountOfJetons.ZERO);
    players.add(player);
  }

  public final AmountOfJetons getHighestBid(final IPlayer player) {
    AmountOfJetons playerAmount = player.getStack().get()
        .plus(player.getPost());
    return AmountOfJetons.min(highestBid, playerAmount);
  }

  public final AmountOfJetons toPay(final IPlayer player) {
    AmountOfJetons playerStack = player.getStack().get();
    AmountOfJetons playerPost = player.getPost();
    return AmountOfJetons.min(highestBid.minus(playerPost), playerStack);
  }

  public final AmountOfJetons computeTotalPot(final IPlayer player) {
    AmountOfJetons sum = AmountOfJetons.ZERO;
    AmountOfJetons playerAmount = player.getStack().get()
        .plus(player.getPost());

    for (SidePot pot : pots) {
      if (pot.getParticipants().contains(player)) {
        sum = sum.plus(pot.getValue());
      }
    }
    for (IPlayer other : players) {
      sum = sum.plus(AmountOfJetons.min(other.getPost(), playerAmount));
    }
    return sum;
  }

  public final String toString() {
    String res = "";
    for (IPlayer player : players) {
      res += player + ": " + player.getPost() + "\n";
    }
    return res;
  }

  public final PayOuts payOut(final IOutcome outcome) {
    PayOuts payOuts = new PayOuts();
    for (SidePot pot : pots) {
      Iterator<IPlayer> i = pot.getParticipants().iterator();

      while (i.hasNext()) {
        IPlayer p = i.next();
        boolean underDog = false;
        for (IPlayer p2 : pot.getParticipants()) {
          if (outcome.betterAs(p2, p)) {
            underDog = true;
          }
        }
        if (underDog) {
          i.remove();
        }
      }

      // split the pot to winners
      AmountOfJetons part = pot.getValue().divide(pot.getParticipants().size());
      for (IPlayer p : pot.getParticipants()) {
        p.getStack().addToStack(part);
        payOuts.add(p, part);
      }
      // some jetons may remain due to division with remainder
      pot.setValue(pot.getValue().minus(part.times(pot.getParticipants().size())));

      // divide the remains by random
      // Collections.shuffle(pot.participants, rand);
      for (IPlayer p : pot.getParticipants()) {
        if (pot.getValue().equals(AmountOfJetons.ZERO)) {
          break;
        }
        pot.setValue(pot.getValue().minus(AmountOfJetons.SB));
        p.getStack().addToStack(AmountOfJetons.SB);
        payOuts.add(p, AmountOfJetons.SB);
      }
    }
    return payOuts;
  }

  @Override
  public final ActionInfo action(final int player, final TypeOfDecision dec) {
    // TODO Auto-generated method stub
    throw new NotImplementedException("todo");
  }
}
