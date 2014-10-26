package managementPayments;

import strategy.PlayerDecision;

public class ActionInfo {
	private final boolean all_in;
	private final boolean raised;
	private final PlayerDecision playerDec;
	private final AmountOfJetons amount;

	public ActionInfo(boolean all_in, boolean raised, PlayerDecision playerDec,
			AmountOfJetons amount) {
		this.all_in = all_in;
		this.raised = raised;
		this.playerDec = playerDec;
		this.amount = amount;
	}

	public boolean isAllIn() {
		return all_in;
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
