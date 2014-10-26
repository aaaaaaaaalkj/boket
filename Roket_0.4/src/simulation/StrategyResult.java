package simulation;

import managementPayments.AmountOfJetons;
import strategy.IStrategy;

public class StrategyResult {
	private final IStrategy strategy;
	private final AmountOfJetons balance;

	private StrategyResult(IStrategy strategy, AmountOfJetons balance) {
		this.strategy = strategy;
		this.balance = balance;
	}

	public IStrategy getStrategy() {
		return strategy;
	}

	public AmountOfJetons getBalance() {
		return balance;
	}

	public static StrategyResult create(IStrategy strategy2,
			AmountOfJetons balance) {
		return new StrategyResult(strategy2, balance);
	}

}
