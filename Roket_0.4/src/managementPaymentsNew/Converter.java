package managementPaymentsNew;

import managementPaymentsNew.decisions.AllowedDecision;
import managementPaymentsNew.decisions.Decision;
import strategy.TypeOfDecision;

public class Converter {
  private final int player;
  private final int toCall;
  private final int lastRaise;
  private final AllowedDecision a;
  private final int pot_size;
  private final int stack_size;

  public Converter(final int player, final IPayManagement2 pay) {
    this.player = player;
    this.toCall = pay.getToCall(player);
    this.lastRaise = pay.getLastRaise();
    this.a = pay.getAllowed(player);
    this.pot_size = pay.getPotSize();
    this.stack_size = pay.getStack(player);
  }

  public final Decision convert(final TypeOfDecision d) {
    int value = 0;

    switch (d) {
    case ALL_IN:
      return Decision.allin();
    case FOLD:
      return Decision.fold();
    case CALL:
      return tryCall(a);
    case POST_BB:
      break;
    case POST_SB:
      break;
    case RAISE_HALF_STACK:
      value = stack_size / 2;
      break;
    case RAISE_FIFTH_STACK:
      value = stack_size / 5;
      break;
    case RAISE_TENTH_STACK:
      value = stack_size / 10;
      break;
    case RAISE_QUARTER_POT:
      value = pot_size / 4;
      break;
    case RAISE_HALF_POT:
      value = pot_size / 2;
      break;
    case RAISE_POT_SIZE:
      value = pot_size;
      break;
    case RAISE_DOUBLE_POT:
      value = pot_size * 2;
      break;
    default:
      throw new AssertionError("Unknown "
          + TypeOfDecision.class.getSimpleName() + ": " + d);
    }
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

  private Decision tryBetRaise(final int value) {
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
