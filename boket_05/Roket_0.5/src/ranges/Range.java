package ranges;

import java.util.Random;

public interface Range {
	boolean contains(ElementRange r);

	int size();

	ElementRange getRandom(Random rnd);

}
