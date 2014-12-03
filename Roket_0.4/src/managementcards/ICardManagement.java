package managementcards;

import java.util.List;

import managementcards.cards.Card;
import managementcards.catrecnew.IResult;

import org.eclipse.jdt.annotation.NonNull;

import common.Round;

public interface ICardManagement {
	void openCards(Round r);

	List<@NonNull Card> getCommunityCards();

	List<@NonNull IResult> getResults();
}
