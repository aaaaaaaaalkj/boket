package input_output;

import java.util.Arrays;

public class Pattern {

	private boolean[] value;

	public Pattern(int num) {
		value = new boolean[num];
		for (int i = 0; i < value.length; i++)
			value[i] = false;
	}

	public Pattern(int... k) {
		value = new boolean[k.length];
		for (int i = 0; i < k.length; i++)
			value[i] = k[i] == 1;
	}

	public void set(int k) {
		value[k] = true;
	}

	@Override
	public boolean equals(Object o) {
		Pattern p;
		if (o instanceof Pattern)
			p = (Pattern) o;
		else
			return false;
		if (p.value.length != value.length)
			return false;
		for (int i = 0; i < value.length; i++) {
			if (value[i] != p.value[i])
				return false;
		}
		return true;
	}

	public String toString() {
		return Arrays.toString(value);
	}
}
