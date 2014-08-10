package managementCards.cat_rec_new;

public interface Result extends Comparable<Result> {
	// Marker-Interface
	// Represents the outcome of the 5 best Cards out of 7
	// Can be used for less than 7 cards
	Cathegory getCathegory();

	// boolean isThreeOfAKind();
}
