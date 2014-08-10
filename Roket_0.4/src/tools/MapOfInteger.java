package tools;

import java.util.HashMap;

public class MapOfInteger<T> {
	HashMap<T, Integer> map = new HashMap<>();

	public void add(T t, Integer i) {
		Integer i2 = get(t);
		put(t, i2 + i);
	}

	private void put(T t, int i) {
		map.put(t, i);
	}

	public Integer get(T t) {
		Integer i2 = map.get(t);
		if (null == i2)
			return 0;
		else
			return i2;
	}

	public void inc(T t) {
		put(t, get(t) + 1);
	}

	public Integer max() {
		Integer max = Integer.MIN_VALUE;
		for (T t : map.keySet())
			max = Math.max(max, get(t));
		return max;

	}

	public T argMax() {
		Integer max = Integer.MIN_VALUE;
		T argMax = null;
		for (T t : map.keySet()) {
			Integer i = get(t);
			if (i > max) {
				max = i;
				argMax = t;
			}
		}
		return argMax;
	}

	public int size() {
		return map.size();
	}

}
