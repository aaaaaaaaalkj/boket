package managementpaymentstmp;

import strategy.PlayerDecision;

public final class ActionInfo {
  private final boolean allIn;
  private final boolean raised;
  private final PlayerDecision playerDec;
  private final AmountOfJetons amount;

  public ActionInfo(
      final boolean allIn,
      final boolean raised,
      final PlayerDecision playerDec,
      final AmountOfJetons amount) {
    this.allIn = allIn;
    this.raised = raised;
    this.playerDec = playerDec;
    this.amount = amount;
  }

  public boolean isAllIn() {
    return allIn;
  }

  public boolean hasRaised() {
    return raised;
  }

  public PlayerDecision getPlayerDec() {
    return playerDec;
  }

  public AmountOfJetons getAmount() {
    return amount;
  }
}
