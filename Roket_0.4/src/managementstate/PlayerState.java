package managementstate;

import common.IPlayer;

/**
 * WAITING = not folded and has still jetons in his stack ALL_IN = not folded and has no jetons in
 * his stack FOLDED = ... SITTING_OUT = FOLDED
 * 
 * @author Combat-Ready
 * 
 */
public enum PlayerState {
  ACTIVE, ALL_IN, FOLDED, SITTING_OUT;

  public static boolean inGame(final PlayerState state) {
    return state == ALL_IN || state == ACTIVE;
  }

  public static boolean inGame(final IPlayer player) {
    return player.getState() == ALL_IN
        || player.getState() == ACTIVE;
  }

  public static boolean isActive(final PlayerState state) {
    return state == ACTIVE;
  }

  public static boolean isActive(final IPlayer player) {
    return player.getState() == ACTIVE;
  }

  public boolean isInGame() {
    return inGame(this);
  }

  public boolean isActive() {
    return isActive(this);
  }
}
