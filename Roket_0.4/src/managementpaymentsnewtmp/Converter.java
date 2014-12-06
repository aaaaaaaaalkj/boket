package managementpaymentsnewtmp;

import managementpaymentsnewtmp.decisions.AllowedDecision;
import managementpaymentsnewtmp.decisions.Decision;
import strategy.TypeOfDecision;

public class Converter {
  private final int player;
  private final int toCall;
  private final int lastRaise;
  private final AllowedDecision a;
  private final int potSize;
  private final int stackSize;

  public Converter(final int player, final IPayManagement2 pay) {
    this.player = player;
    this.toCall = pay.getToCall(player);
    this.lastRaise = pay.getLastRaise();
    this.a = pay.getAllowed(player);
    this.potSize = pay.getPotSize();
    this.stackSize = pay.getStack(player);
  }

  /**
   * 
   * @param decision
   *          - low-level decision
   * @return - high-level decision
   */
  public final Decision convert(final TypeOfDecision decision) {

    if (decision == TypeOfDecision.ALL_IN) {
      return Decision.allin();
    }
    if (decision == TypeOfDecision.FOLD) {
      return Decision.fold();
    }
    if (decision == TypeOfDecision.POST_BB) {
      return tryBetRaise(0);
    }
    if (decision == TypeOfDecision.POST_SB) {
      return tryBetRaise(0);
    }
    if (decision == TypeOfDecision.CALL) {
      return tryCall(a);
    }

    int value = 0;
    if (decision.isPotCentred()) {
      value = potSize;
    } else {
      value = stackSize;
    }
    // decision has a factor for the pot or stack which determines how much to bet
    value = (int) (value * decision.getFactor());

    return tryBetRaise(value);
  }

  private Decision tryCall(final AllowedDecision a) {
    if (a.isCheckBetAllowed()) {
      return Decision.check();
    } else if (a.isCallAllowed()) {
      return Decision.call();
    } else {
      return Decision.allin();
    }
  }

  private Decision tryBetRaise(final int value1) {
    int value = value1;
    if (a.isRaiseAllowed()) {
      if (value < toCall / 2) {
        // value is far to small even for a call
        return Decision.fold();
      }
      if (value < toCall + lastRaise) {
        // value is too small for a raise
        return Decision.call();
      } else {
        value -= toCall;
        value -= lastRaise;
        assert value >= 0;
        if (value > a.maxBetRaiseAllowed()) {
          // value is too big for a regular raise
          return Decision.allin();
        } else {
          // value is ok. just raise
          return Decision.raise(value);
        }
      }

    } else if (a.isCheckBetAllowed()) {
      if (value == 0) {
        // raise 0
        return Decision.check();
      } else if (value <= a.maxBetRaiseAllowed()) {
        // regular case
        return Decision.bet(value);
      } else {
        // value is too big for a raise
        return Decision.allin();
      }
    } else if (a.isCallAllowed()) {
      // not enough money for a raise
      if (value > toCall) {
        // value too big for a call
        return Decision.allin();
      } else {
        return Decision.call();
      }
    } else {
      // only fold or allin is allowed here
      return Decision.allin();
    }
  }

  public final int getPlayer() {
    return player;
  }
}
