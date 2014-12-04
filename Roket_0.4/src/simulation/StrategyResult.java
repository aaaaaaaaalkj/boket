package simulation;

import managementpaymentstmp.AmountOfJetons;
import strategy.IStrategy;

public final class StrategyResult {
	private final IStrategy strategy;
	private final AmountOfJetons balance;

  private StrategyResult(final IStrategy strategy, final AmountOfJetons balance) {
		this.strategy = strategy;
		this.balance = balance;
	}

	public IStrategy getStrategy() {
		return strategy;
	}

	public AmountOfJetons getBalance() {
		return balance;
	}

  public static StrategyResult create(final IStrategy strategy2,
      final AmountOfJetons balance) {
		return new StrategyResult(strategy2, balance);
	}

}
