package tools;

public class Logger {
	public void info(Object s) {
		info(s.toString());
	}

	public void info(String s) {
		System.out.println(s);
	}

	public void warning(String string) {
		System.out.println(string);
	}

	public void exit(Throwable e) {
		e.printStackTrace();
		System.exit(1);
	}

	public void exit(String string) {
		throw new RuntimeException("exit: " + string);
	}

	public void emptyLine() {
		info("");
	}
}
