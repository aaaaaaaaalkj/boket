package managementpaymentsnewtmp;

import java.util.List;
import java.util.Set;

import managementcards.catrecnew.IResult;
import managementpaymentsnewtmp.decisions.AllowedDecision;
import managementpaymentsnewtmp.decisions.Decision;
import strategy.TypeOfDecision;
import strategy.conditions.common.PotType;

public interface IPayManagement2 {

	void collectPostsToSidePots();

	void action(int player, Decision decision);

	default Decision action(int player, TypeOfDecision decision) {
		Decision dec = new Converter(player, this).convert(decision);
		action(player, dec);
		return dec;
	}

	int getToCall(int player);

	int getLastRaise();

	PotType getPotType(int currentPlayer);

	int getStack(int player);

	AllowedDecision getAllowed(int player);

	int getPotSize();

	List<Integer> payOut(Set<Integer> notFolded, List<IResult> results);

}
