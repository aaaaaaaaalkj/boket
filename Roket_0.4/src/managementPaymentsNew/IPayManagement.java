package managementPaymentsNew;

import managementPayments.ActionInfo;
import managementPayments.PayOuts;
import strategy.TypeOfDecision;

import common.IOutcome;

public interface IPayManagement {
	ActionInfo action(int player, TypeOfDecision dec);

	PayOuts payOut(IOutcome outcome);

}
