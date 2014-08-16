package input_output;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tools.Pos;

public class MyOutput {
	Robot robot;

	public MyOutput() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l
					+ " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	public void type2(double d, Pos logo) {
		moveAndDoubleClick(new Pos(630, 480).plus(logo));
		type(d);
	}

	public void clickFoldButton(Pos logo) {
		moveAndClick(new Pos(470, 530).plus(logo));
	}

	public void clickCallButton(Pos logo) {
		moveAndClick(new Pos(600, 530).plus(logo));
	}

	public void clickRaiseButton(Pos logo) {
		moveAndClick(new Pos(700, 550).plus(logo));
	}

	public void moveAndClick(Pos p) {
		robot.mouseMove(p.x, p.y);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public void moveAndDoubleClick(Pos p) {
		robot.mouseMove(p.x, p.y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public void type(double d) {
		if (d < 0) {
			throw new IllegalArgumentException(
					"its not allowed to type negative numbers");
		}

		int i = safeLongToInt(Math.round(d * 100));

		List<Integer> digits = digits(i);

		for (int index = 0; index < digits.size(); index++) {

			if (index == digits.size() - 2) {
				robot.keyPress(KeyEvent.VK_PERIOD);
				robot.keyRelease(KeyEvent.VK_PERIOD);
			}

			Integer j = digits.get(index);
			switch (j) {
			case 0:
				robot.keyPress(KeyEvent.VK_0);
				robot.keyRelease(KeyEvent.VK_0);
				break;
			case 1:
				robot.keyPress(KeyEvent.VK_1);
				robot.keyRelease(KeyEvent.VK_1);
				break;
			case 2:
				robot.keyPress(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_2);
				break;
			case 3:
				robot.keyPress(KeyEvent.VK_3);
				robot.keyRelease(KeyEvent.VK_3);
				break;
			case 4:
				robot.keyPress(KeyEvent.VK_4);
				robot.keyRelease(KeyEvent.VK_4);
				break;
			case 5:
				robot.keyPress(KeyEvent.VK_5);
				robot.keyRelease(KeyEvent.VK_5);
				break;
			case 6:
				robot.keyPress(KeyEvent.VK_6);
				robot.keyRelease(KeyEvent.VK_6);
				break;
			case 7:
				robot.keyPress(KeyEvent.VK_7);
				robot.keyRelease(KeyEvent.VK_7);
				break;
			case 8:
				robot.keyPress(KeyEvent.VK_8);
				robot.keyRelease(KeyEvent.VK_8);
				break;
			case 9:
				robot.keyPress(KeyEvent.VK_9);
				robot.keyRelease(KeyEvent.VK_9);
				break;
			default:
				System.out.println("shit happens");
			}
		}

	}

	List<Integer> digits(int i) {
		List<Integer> digits = new ArrayList<Integer>();

		if (i == 0) {
			digits.add(i);
			return digits;
		}

		while (i > 0) {
			digits.add(i % 10);
			i /= 10;
		}
		Collections.reverse(digits);

		return digits;
	}
}
