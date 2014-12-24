package ranges.atry;

import tools.Tools;

public class ScoreInterval {
  private final double minValue;
  private final double maxValue;

  public ScoreInterval(double minValue, double maxValue) {
    Tools.checkArg(minValue, 0, 1);
    Tools.checkArg(maxValue, 0, 1);
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  public double getMinValue() {
    return minValue;
  }

  public double getMaxValue() {
    return maxValue;
  }

}
