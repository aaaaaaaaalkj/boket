package simulation;

import managementPayments.AmountOfJetons;
import strategy.IStrategy;

public interface IStrategyResult {

	public IStrategy getStrategy();

	public AmountOfJetons getBalance();

}
