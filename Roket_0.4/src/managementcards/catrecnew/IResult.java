package managementcards.catrecnew;

public interface IResult extends Comparable<IResult> {
	// Represents the outcome of the 5 best Cards out of 7
	// Can be used for less than 7 cards
	Cathegory getCathegory();
}
