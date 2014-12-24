package start;

public class Test {
  public static void main(String[] args) {

    for (int first = 0; first <= 12; first++) {
      System.out.println(first + ": " + test(first));
    }

  }

  public static double test(int first) {
    double n = 12 - first;
    return 2 * n * (n / 2 + first + 1.5) - 12 + first;
  }

}
