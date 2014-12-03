package simulation;

import managementPayments.AmountOfJetons;
import strategy.IStrategy;

public interface IStrategyResult {

  IStrategy getStrategy();

  AmountOfJetons getBalance();

}
