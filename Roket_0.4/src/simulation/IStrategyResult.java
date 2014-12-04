package simulation;

import managementpaymentstmp.AmountOfJetons;
import strategy.IStrategy;

public interface IStrategyResult {

  IStrategy getStrategy();

  AmountOfJetons getBalance();

}
