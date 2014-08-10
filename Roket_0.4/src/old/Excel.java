package old;

//import java.io.File;
//import java.io.IOException;
//
//import strategy.Decision;
//import strategy.Position;
//import strategy.PreflopBuket;
//import strategy.PreflopSelector;
//import strategy.PreflopSituation;
//import strategy.PreflopStrategy;
//import tools.Logger;

//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.read.biff.BiffException;

public class Excel {
//	private static Sheet sheet;
//	private static PreflopStrategy preflopStrategy;
//
//	public static void main(String[] args) {
//		start();
////		PreflopSelector sel = new PreflopSelector();
////		sel.setBuket(PreflopBuket.worthless);
////		sel.setPosition(Position.CO);
////		sel.setSituation(PreflopSituation.oneRaiseAfterYou);
////		PreflopSelector sel2 = new PreflopSelector();
////		sel2.setBuket(PreflopBuket.worthless);
////		sel2.setPosition(Position.CO);
////		sel2.setSituation(PreflopSituation.oneRaiseAfterYou);
////		preflopStrategy.put(sel, Decision.CALL);
////		Decision d = preflopStrategy.get(sel2);		
////		System.out.println(d);
//	}
//
//	public static PreflopStrategy getPreflopStrategy() {
//		return preflopStrategy;
//	}
//
//	public static void start() {
//		sheet = getSheet();
//		preflopStrategy = new PreflopStrategy();
//
//		for (int y = 2; y < sheet.getRows(); y++) {
//			for (int x = 1; x < sheet.getColumns(); x++) {
//				String s = sheet.getCell(x, 1).getContents();
//				if (s.equals("UTG")) {
//					add(x, y, "UTG1");
//					add(x, y, "UTG2");
//					add(x, y, "UTG3");
//				} else if (s.equals("MP")) {
//					add(x, y, "MP1");
//					add(x, y, "MP2");
//					add(x, y, "MP3");
//				} else
//					add(x, y, s);
//			}
//			System.out.println();
//		}
//	}
//
//	public static void add(int x, int y, String pos) {
//		PreflopSelector preflopSelector;
//		Decision d = null;
//		String s;
//		String cell;
//
//		preflopSelector = new PreflopSelector();
//
//		Position p = Position.valueOf(pos);
//
//		cell = sheet.getCell(x, y).getContents();
//
//		s = sheet.getCell(0, y).getContents();
//		PreflopSituation preflopSit = PreflopSituation
//				.valueOf(stringToJavaConvention(s));
//
//		s = sheet.getCell(x, 0).getContents();
//		PreflopBuket buket = PreflopBuket.valueOf(s);
//
//		try {
//			d = Decision.valueOf(cell.toUpperCase());
//		} catch (Throwable t) {
//			System.out.println("position: " + p);
//			System.out.println("preflopSit: " + preflopSit);
//			System.out.println("buket : " + buket);
//			Logger.exit(t);
//		}
//		if (null == d) {
//			Logger.exit("decision is null");
//		}
//
//		preflopSelector.setSituation(preflopSit);
//		preflopSelector.setPosition(p);
//		preflopSelector.setBuket(buket);
//
//		preflopStrategy.put(preflopSelector, d);
//
//	}
//
//	public static String stringToJavaConvention(String s) {
//		String[] sa = s.split(" ");
//		String stringRes = sa[0].toLowerCase();
//		for (int i = 1; i < sa.length; i++) {
//			stringRes = stringRes + sa[i].substring(0, 1).toUpperCase()
//					+ sa[i].substring(1);
//		}
//		return stringRes;
//	}
//
//	public static Sheet getSheet() {
//		String folder = "D:\\Dropbox\\Boket\\reloaded\\";
//		try {
//			Workbook workbook = Workbook.getWorkbook(new File(folder
//					+ "strategy.xls"));
//			Sheet sheet = workbook.getSheet(0);
//			return sheet;
//		} catch (BiffException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

}
