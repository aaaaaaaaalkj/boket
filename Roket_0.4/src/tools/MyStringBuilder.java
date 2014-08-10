package tools;

public class MyStringBuilder {
	private StringBuilder builder = new StringBuilder();
	private String cache = null;

	public MyStringBuilder() {

	}

	public void add(Object s) {
		add(s.toString());
	}

	public void add(String s) {
		if (null != cache) {
			builder.append(cache);
		}
		cache = s;
	}

	public String toString() {
		if (null != cache) {
			builder.append(cache);
			cache = null;
		}
		return builder.toString();
	}

	public void remove(String string) {
		if (cache == null)
			throw new IllegalStateException("Cant remove " + string
					+ " because cache is empts");
		if (!cache.equals(string)) {
			throw new IllegalStateException("Cant remove " + string
					+ " because cache contains " + cache);
		}
		cache = null;
	}
}
