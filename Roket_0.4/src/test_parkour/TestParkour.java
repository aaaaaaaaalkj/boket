package test_parkour;

import input_output.Raw_Situation;
import input_output.ScreenScraper;

import java.awt.AWTException;

import org.slf4j.LoggerFactory;

public class TestParkour {
	@SuppressWarnings("null")
  static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(TestParkour.class);

  public final void test() throws AWTException {
		ScreenScraper scraper = new ScreenScraper();
		Raw_Situation raw = scraper.getSituation();
		logger.info(raw.toString2());
	}

  public static void main(final String[] args) throws AWTException {
		new TestParkour().test();
	}
}
