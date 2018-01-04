package ranges.atry;

public class IndexInterval {
  private final int startIndex;
  private final int endIndex;

  public IndexInterval(
      Integer startIndex,
      Integer endIndex) {
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }
}
