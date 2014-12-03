package tools;

public final class StopWatch {

  private StopWatch() {

  }

  private static long start;

  public static void start() {
    start = System.currentTimeMillis();
  }

  public static void stop() {
    long l = System.currentTimeMillis() - start;
    System.out.println(l);
  }
}
