package managementCards;

import java.util.List;

import managementCards.cards.Card;
import managementCards.cat_rec_new.IResult;

import org.eclipse.jdt.annotation.NonNull;

import common.Round;

public interface ICardManagement {
	void openCards(Round r);

	List<@NonNull Card> getCommunityCards();

	List<@NonNull IResult> getResults();
}
