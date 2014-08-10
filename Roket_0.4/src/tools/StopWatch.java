package tools;

public class StopWatch {
	private static long start;

	public static void start() {
		start = System.currentTimeMillis();
	}

	public static void stop() {
		long l = System.currentTimeMillis() - start;
		System.out.println(l);
	}
}
