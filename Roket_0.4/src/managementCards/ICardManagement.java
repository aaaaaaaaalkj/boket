package managementCards;

import java.util.List;

import managementCards.cards.Card;
import managementCards.cat_rec_new.IResult;

import common.Round;

public interface ICardManagement {
	void openCards(Round r);

	List<Card> getCommunityCards();

	List<IResult> getResults();
}
