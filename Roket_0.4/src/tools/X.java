package tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamTokenizer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class X {
	private static java.util.Random r = new java.util.Random();
	public final static String[] SYMBOLS = { "null", "H", "He", "Li", "Be",
			"B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S",
			"Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co",
			"Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr",
			"Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In" };

	public static boolean every(double[] ar, double d) {
		for (int i = 0; i < ar.length; i++)
			if (ar[i] != d)
				return false;
		return true;
	}

	public static int random(int start, int end) {
		assertTrue(end >= start);
		return (int) Math.floor((end - start + 1) * r.nextDouble() + start);
	}

	public static double random() {
		return r.nextDouble();
	}

	public static double[][] readMatrix(String f) {

		double[][] K = new double[940][940];
		FileReader fr = null;
		try {
			fr = new FileReader(f);
			StreamTokenizer st = new StreamTokenizer(fr);
			st.eolIsSignificant(true);
			st.parseNumbers();

			while (st.nextToken() != StreamTokenizer.TT_EOF) {
				st.pushBack();
				ArrayList<Double> row = new ArrayList<Double>();
				while (st.nextToken() != StreamTokenizer.TT_EOL) {

					if (st.ttype == StreamTokenizer.TT_EOF) {
						break;
					}
					if (st.ttype == StreamTokenizer.TT_NUMBER) {
						row.add(new Double(st.nval));
					} else {
						throw new IOException("\t Invalid token!");
					}
				}
				int n = row.size();
				int i1 = row.get(0).intValue();
				int i2 = row.get(1).intValue();
				double factor = row.get(2).doubleValue();
				K[i1][i2] = 0;
				for (int i = 3; i < n; i++) {
					K[i1][i2] += row.get(i).intValue();
				}
				K[i1][i2] /= factor;
			}
			return K;
		} catch (IOException e) {
			System.err.println("Error! Reading matrix failed.");
			System.err.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Error! Invalid number format.");
			System.err.println(e.getMessage());
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static int max(int[] ar) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] > max)
				max = ar[i];
		}
		return max;
	}

	public static double max(double[] ar) {
		double max = -Double.MAX_VALUE;
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] > max)
				max = ar[i];
		}
		return max;
	}

	public static double max(double a, double b) {
		return (a > b) ? a : b;
	}

	public static double min(double a, double b) {
		return (a < b) ? a : b;
	}

	public static int max(int a, int b) {
		return (a > b) ? a : b;
	}

	public static int min(int a, int b) {
		return (a < b) ? a : b;
	}

	public static String toString(boolean[] ar) {
		String s = "[ ";
		for (int i = 0; i < ar.length; i++)
			s += ar[i] + (i == ar.length - 1 ? "" : ", ");
		return s + " ]";
	}

	public static String toString(byte[] ar) {
		String s = "[ ";
		for (int i = 0; i < ar.length; i++)
			s += ar[i] + (i == ar.length - 1 ? "" : ", ");
		return s + " ]";
	}

	public static String toString(int[] ar) {
		String s = "[ ";
		for (int i = 0; i < ar.length; i++)
			s += ar[i] + (i == ar.length - 1 ? "" : ", ");
		return s + " ]";
	}

	public static String toString(double[] ar) {
		return toString(ar, 2);
	}

	public static String toString(double[] ar, int precision) {
		if (null == ar)
			return "null";
		double faktor = Math.pow(10, precision);
		String s = "[ ";
		for (int i = 0; i < ar.length; i++) {
			if (Double.isNaN(ar[i]))
				s += "NaN";
			else
				s += ((double) Math.round(ar[i] * faktor) / faktor);
			s += i == ar.length - 1 ? "" : ", ";
		}
		return s + " ]";
	}

	public static String toString(Object[] ar) {
		String s = "\n[ ";
		for (int i = 0; i < ar.length; i++)
			s += ar[i] + (i == ar.length - 1 ? "" : ", ");
		return s + " ]\n";
	}

	public static void print(double d) {
		print(d);
	}

	public static void print(Object[] ar) {
		print(toString(ar));
	}

	public static void print(Object o) {
		print(o.toString());
	}

	public static void print(String s) {
		System.out.println(s);
	}

	public static String toString(int[][] A) {
		return toString(A, true);
	}

	public static String toString(int[][] A, boolean withZeros) {
		String s = "";
		int max = 0;
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				String v = Integer.toString(A[i][j]);
				if (!withZeros && A[i][j] == 0)
					v = "_";
				if (v.length() > max)
					max = v.length();
			}
		}

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[i].length; j++) {
				String v = Integer.toString(A[i][j]);
				if (!withZeros && A[i][j] == 0)
					v = "_";
				int dif = max - v.length();
				while (dif-- > 0)
					v = " " + v;
				s += v;
				if (j < A[i].length - 1)
					s += ",";
			}
			if (i < A.length - 1)
				s += "\n";
		}
		return s;
	}

	public static double round(double d) {
		return round(d, 2);
	}

	public static double round(double d, int digits) {
		double l = Math.pow(10, digits);
		return Math.floor(d * l) / l;
	}

	public static double[][] round(double[][] A) {
		double[][] B = new double[A.length][];
		double f = 100;
		for (int i = 0; i < A.length; i++) {
			if (A[i] == null)
				continue;
			B[i] = new double[A[i].length];
			for (int j = 0; j < A[i].length; j++) {
				B[i][j] = Math.round(A[i][j] * f) / f;
			}
		}
		return B;
	}

	public static String toString(double[][] A) {
		if (A == null)
			return "--------\n null \n ----------";
		A = round(A);

		int before = 0;
		int after = 0;

		for (int i = 0; i < A.length; i++) {
			if (A[i] == null)
				continue;
			for (int j = 0; j < A[i].length; j++) {
				String s = Double.toString(A[i][j]);
				if (s.contains(".")) {
					String[] spl = s.split("\\.");
					before = Math.max(before, spl[0].length());
					after = Math.max(after, spl[1].length());
				}
			}
		}
		String res = "\n------------------\n";
		for (int i = 0; i < A.length; i++) {
			if (A[i] == null) {
				res += "null";
			} else
				for (int j = 0; j < A[i].length; j++) {
					String s = Double.toString(A[i][j]);
					if (s.contains(".")) {
						int b = s.split("\\.")[0].length();
						int a = s.split("\\.")[1].length();
						while (b++ < before)
							s = " " + s;
						while (a++ < after)
							s = s + " ";
					} else {
						int length = s.length();
						while (length++ < before + 1 + after)
							s += " ";
					}
					res += s + " ";
				}
			res += "\n";
		}
		return res + "--------------------\n";
	}

	public static void print(int[] ar) {
		print(toString(ar));
	}

	public static void print(double[] ar) {
		print(toString(ar));
	}

	public static void print(double[][] ar) {
		print(toString(ar));
	}

	public static void print(boolean[] ar) {
		print(toString(ar));
	}

	public static boolean isSorted(double[] X) {
		for (int i = 1; i < X.length; i++)
			if (X[i] <= X[i - 1])
				return false;
		return true;
	}

	private static int[] permut;

	public static int[] sort(double[] X) {
		permut = new int[X.length];
		for (int i = 0; i < permut.length; i++)
			permut[i] = i;
		sort1(X, 0, X.length);

		return inverse(permut);
	}

	private static void sort1(double x[], int off, int len) {
		// Insertion sort on smallest arrays
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off && x[j - 1] > x[j]; j--)
					swap(x, j, j - 1, true);
			return;
		}

		// Choose a partition element, v
		int m = off + (len >> 1); // Small arrays, middle element
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) { // Big arrays, pseudomedian of 9
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n); // Mid-size, med of 3
		}
		double v = x[m];

		// Establish Invariant: v* (<v)* (>v)* v*
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			while (b <= c && x[b] <= v) {
				if (x[b] == v)
					swap(x, a++, b, true);
				b++;
			}
			while (c >= b && x[c] >= v) {
				if (x[c] == v)
					swap(x, c, d--, true);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--, true);
		}

		// Swap partition elements back to middle
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);

		// Recursively sort non-partition-elements
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}

	/**
	 * Swaps x[a] with x[b].
	 */
	private static void swap(double x[], int a, int b, boolean bb) {
		double t = x[a];
		x[a] = x[b];
		x[b] = t;
		int s = permut[a];
		permut[a] = permut[b];
		permut[b] = s;
	}

	/**
	 * Swaps x[a .. (a+n-1)] with x[b .. (b+n-1)].
	 */
	private static void vecswap(double x[], int a, int b, int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b, true);
	}

	/**
	 * Returns the index of the median of the three indexed doubles.
	 */
	private static int med3(double x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a)
				: (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}

	// public static <T> void permut(T[] ar, int[] p) {
	// int[] visited = new int[p.length];
	// int pos = 0;
	// T dummy, dummy2;
	//
	// while (visited[pos] == 0) {
	// dummy = ar[pos];
	// ar[pos] = ar[p[pos]];
	// visited[pos] = 1;
	// pos = p[pos];
	// }
	//
	// int i = p[1];
	// while (i != p[0]) {
	// }
	// }

	public static double[] permut(double[] ar, int[] p) {
		double[] res = new double[ar.length];
		for (int i = 0; i < p.length; i++) {
			res[i] = ar[p[i]];
		}
		return res;
	}

	public static int[][] permut(int[][] ar, int[] p) {
		int[][] res = new int[ar.length][];
		for (int i = 0; i < p.length; i++) {
			res[i] = ar[p[i]];
		}
		return res;
	}

	public static double[] reverse(double[] ar) {
		double[] res = new double[ar.length];
		for (int i = 0; i < ar.length; i++)
			res[ar.length - 1 - i] = ar[i];
		return res;
	}

	public static <T> void reverse(T[] ar) {
		T dummy;
		for (int i = 0; i < ar.length >> 1; i++) {
			dummy = ar[i];
			ar[i] = ar[ar.length - 1 - i];
			ar[ar.length - 1 - i] = dummy;
		}

	}

	public static int[] reverse(int[] ar) {
		int[] res = new int[ar.length];
		for (int i = 0; i < ar.length; i++)
			res[ar.length - 1 - i] = ar[i];
		return res;
	}

	public static double[] reduceLength(double[] d, int length) {
		double[] res = new double[length];
		for (int i = 0; i < length; i++)
			res[i] = d[i];
		return res;
	}

	public static int[] reduceLength(int[] d, int length) {
		int[] res = new int[length];
		for (int i = 0; i < length; i++)
			res[i] = d[i];
		return res;
	}

	public static int[][] reduceLength(int[][] d, int length) {
		int[][] res = new int[length][];
		for (int i = 0; i < length; i++)
			res[i] = d[i];
		return res;
	}

	public static int[] inverse(int[] ar) {
		if (ar.length == 0)
			return new int[0];
		int[] res = new int[max(ar) + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = -1;
		}
		for (int i = 0; i < ar.length; i++) {
			res[ar[i]] = i;
		}
		return res;
	}

	public static double[] times(double[] a, double b) {
		double[] res = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			res[i] = a[i] * b;
		}
		return res;
	}

	public static double[][] times(double[][] a, double b) {
		double[][] res = new double[a.length][];
		for (int i = 0; i < a.length; i++) {
			res[i] = times(a[i], b);
		}
		return res;
	}

	public static double[][][] times(double[][][] a, double b) {
		double[][][] res = new double[a.length][][];
		for (int i = 0; i < a.length; i++) {
			res[i] = times(a[i], b);
		}
		return res;
	}

	public static double[] timesComp(double[] a, double[] b) {
		assertTrue(a.length == b.length, "Arguments must have same length!");

		double[] res = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			res[i] = a[i] * b[i];
		}
		return res;
	}

	public static double skalProd(double[] a, double[] b) {
		return skalProd(a, b, 0);
	}

	public static double skalProd(double[] a, double[] b, int start) {
		double res = 0;
		for (int i = start; i < a.length; i++) {
			res += a[i] * b[i];
		}
		return res;
	}

	public static double skalProd(double[][][] a, double[][][] b) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				sum += X.skalProd(a[i][j], b[i][j]);
			}
		}
		return sum;
	}

	public static int argMax(int[] d) {
		int max = Integer.MIN_VALUE;
		int argMax = -1;

		for (int i = 0; i < d.length; i++) {
			if (d[i] > max) {
				max = d[i];
				argMax = i;
			}
		}
		return argMax;

	}

	public static int argMax(double[] d) {
		double max = -Double.MAX_VALUE;
		int argMax = -1;

		for (int i = 0; i < d.length; i++) {
			if (d[i] > max) {
				max = d[i];
				argMax = i;

			}
		}
		return argMax;
	}

	public static int argMin(double[] d) {
		double min = Double.MAX_VALUE;
		int argMin = -1;
		for (int i = 0; i < d.length; i++) {
			if (d[i] < min) {
				min = d[i];
				argMin = i;
			}
		}
		return argMin;
	}

	public static double[] plus(double[] a, double[] b) {
		assertTrue(a.length == b.length, "Arguments must have same size!");

		double[] res = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			res[i] = a[i] + b[i];
		}
		return res;
	}

	public static double[] minus(double[] a, double[] b) {
		assertTrue(a.length == b.length, "Arguments must have same size!");

		double[] res = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			res[i] = a[i] - b[i];
		}
		return res;
	}

	public static double[] square(double[] d) {
		double[] res = new double[d.length];
		for (int i = 0; i < d.length; i++)
			res[i] = d[i] * d[i];
		return res;
	}

	public static double[] sqrt(double[] d) {
		double[] res = new double[d.length];
		for (int i = 0; i < d.length; i++)
			res[i] = Math.sqrt(d[i]);
		return res;
	}

	public static double[][] sqrt(double[][] d) {
		double[][] res = new double[d.length][];
		for (int i = 0; i < d.length; i++)
			res[i] = sqrt(d[i]);
		return res;
	}

	public static double[][][] sqrt(double[][][] d) {
		double[][][] res = new double[d.length][][];
		for (int i = 0; i < d.length; i++)
			res[i] = sqrt(d[i]);
		return res;
	}

	public static double[] reciprocal(double[] d) {
		double[] res = new double[d.length];
		for (int i = 0; i < d.length; i++)
			res[i] = 1. / d[i];
		return res;
	}

	public static double[] mal(double[] d, double lambda) {
		double[] res = new double[d.length];
		for (int i = 0; i < d.length; i++)
			res[i] = d[i] * lambda;
		return res;
	}

	public static void shuffle(int[] ar) {
		for (int i = 0; i < ar.length; i++) {
			int pos = (int) Math.floor(r.nextDouble() * ar.length);
			int dummy = ar[i];
			ar[i] = ar[pos];
			ar[pos] = dummy;
		}
	}

	public static <T> void shuffle(T[] ar) {
		for (int i = 0; i < ar.length; i++) {
			int pos = (int) Math.floor(r.nextDouble() * ar.length);
			swap(ar, i, pos);
		}
	}

	public static <T> void swap(T[] ar, int i, int j) {
		T dummy = ar[i];
		ar[i] = ar[j];
		ar[j] = dummy;
	}

	public static int[] nats(int n) {
		int[] perm = new int[n];
		for (int i = 0; i < n; i++)
			perm[i] = i;
		return perm;
	}

	public static double[] replace(double[] d, double a, double b) {
		double[] res = new double[d.length];
		for (int i = 0; i < d.length; i++)
			if (d[i] == a)
				res[i] = b;
			else
				res[i] = d[i];
		return res;
	}

	public static int[] fromIntegerAr(Integer[] ar) {
		int[] ar2 = new int[ar.length];
		for (int i = 0; i < ar.length; i++)
			ar2[i] = ar[i];
		return ar2;
	}

	public static void fill(double[] d, double v) {
		Arrays.fill(d, v);
	}

	public static void fill(boolean[] d, boolean v) {
		Arrays.fill(d, v);
	}

	public static void fill(int[] d, int v) {
		Arrays.fill(d, v);
	}

	public static void fill(double[][] d, double v) {
		for (int i = 0; i < d.length; i++)
			Arrays.fill(d[i], v);
	}

	public static void fill(int[][] d, int v) {
		for (int i = 0; i < d.length; i++)
			Arrays.fill(d[i], v);
	}

	/*
	 * Dont use following method! This method is not defined for arrays with
	 * length == 0.
	 */

	// @SuppressWarnings("unchecked")
	// private static <T> T[] cast(Object array) {
	// if (!array.getClass().isArray()) {
	// throw new RuntimeException("Argument is not an array!");
	// }
	// if (Array.getLength(array) == 0)
	// throw new RuntimeException(
	// "'cast'-method is not defined for arrays with length == 0!");
	// Class<T> c = (Class<T>) Array.get(array, 0).getClass();
	//
	// T[] cArr = (T[]) Array.newInstance(c, Array.getLength(array));
	// // shallow-copy
	// for (int i = 0; i < Array.getLength(array); i++)
	// cArr[i] = (T) Array.get(array, i);
	// return cArr;
	// }

	public static int[] castIntArr(Object array) {
		if (!array.getClass().isArray()) {
			throw new RuntimeException("Argument is not an array!");
		}
		int[] res = new int[Array.getLength(array)];
		for (int i = 0; i < res.length; i++) {
			res[i] = (int) (Integer) Array.get(array, i);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] cast(Object array, Class<T> c) {
		if (!array.getClass().isArray()) {
			throw new RuntimeException("Argument is not an array!");
		}

		Object o = Array.newInstance(c, Array.getLength(array));
		T[] cArr;
		cArr = (T[]) o;

		// shallow-copy
		System.arraycopy(array, 0, cArr, 0, Array.getLength(array));
		return cArr;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] cast(Object array) {
		if (!array.getClass().isArray()) {
			throw new RuntimeException("Argument is not an array!");
		}
		T[] cArr = (T[]) Array.newInstance(array.getClass().getComponentType(),
				Array.getLength(array));
		// shallow-copy
		System.arraycopy(array, 0, cArr, 0, Array.getLength(array));
		return cArr;
	}

	public static int[] cast(double[] d) {
		int[] res = new int[d.length];
		for (int i = 0; i < res.length; i++)
			res[i] = (int) d[i];
		return res;
	}

	public static Object deepCopy(Object array) {
		if (array == null)
			return null;
		Class<?> clazz = array.getClass();

		// sofern kein Array: Clone oder Original zurückgeben
		if (!clazz.isArray()) {
			if (Cloneable.class.isAssignableFrom(clazz)) {
				try {
					return clazz.getMethod("clone").invoke(array);
				} catch (Exception e) {
					// tritt nur in merkwürdigen protected Implementierungen
					// auf.. offenbar nicht nur :(
					throw new Error(e);
				}
			}
			return array;
		}
		// Anlage eines Arrays
		Object cArr = Array.newInstance(array.getClass().getComponentType(),
				Array.getLength(array));

		// shallow-copy für Primitive
		if (cArr.getClass().getComponentType().isPrimitive()) {
			System.arraycopy(array, 0, cArr, 0, Array.getLength(array));
		}

		// nicht primitive Komponenten werden rekursiv kopiert
		for (int i = 0; i < Array.getLength(cArr); i++) {
			Array.set(cArr, i, deepCopy(Array.get(array, i)));
		}
		return cArr;
	}

	public static void warnIf(boolean b, String description) {
		if (b)
			System.err.println(description);
	}

	public static void assertTrue(boolean b) {
		assertTrue(b, "");
	}

	public static void assertTrue(boolean b, String description) {
		if (!b)
			throw new RuntimeException("Assertion error. " + description);
	}

	public static void assertFalse(boolean b) {
		assertFalse(b, "");
	}

	public static void assertFalse(boolean b, String description) {
		if (b)
			throw new RuntimeException("Assertion error. " + description);
	}

	public static double[] copyOf(double[] ar) {
		return Arrays.copyOf(ar, ar.length);
	}

	public static int[] copyOf(int[] ar) {
		return Arrays.copyOf(ar, ar.length);
	}

	public static boolean contains(double[] ar, double d) {
		for (int i = 0; i < ar.length; i++) {
			if (d == ar[i] || Double.isNaN(d) && Double.isNaN(ar[i]))
				return true;
		}
		return false;
	}

	public static boolean contains(double[][] ar, double d) {
		for (int i = 0; i < ar.length; i++) {
			if (null != ar[i])
				if (contains(ar[i], d))
					return true;
		}
		return false;
	}

	public static boolean contains(double[][][] ar, double d) {
		for (int i = 0; i < ar.length; i++) {
			if (null != ar[i])
				if (contains(ar[i], d))
					return true;
		}
		return false;
	}

	public static boolean contains(double[][][][] ar, double d) {
		for (int i = 0; i < ar.length; i++) {
			if (null != ar[i])
				if (contains(ar[i], d))
					return true;
		}
		return false;
	}

	/**
	 * Returns the index of element in array or -1 if array doesn't contain
	 * element. Uses "==" for comparison. null elements are allowed.
	 */
	public static int indexOf(Object[] array, Object element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element)
				return i;
		}
		return -1;
	}

	/**
	 * Returns the index of element in array or -1 if array doesn't contain
	 * element. Uses "equals" for comparison. null elements are allowed.
	 */
	public static int indexOfEquals(Object[] array, Object element) {
		if (element == null)
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null)
					return i;
			}
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(element))
				return i;
		}
		return -1;
	}

	/**
	 * Returns true if array contains element. Uses Object.equals() for
	 * comparison. No null elements allowed.
	 */
	public static boolean contains(Object[] array, Object element) {
		return indexOf(array, element) >= 0;
	}

	public static int randomIndex(Object[] ar) {
		return randomIndex(ar.length);
	}

	public static int randomIndex(ArrayList<?> ar) {
		return randomIndex(ar.size());
	}

	public static int randomIndex(int length) {
		return (int) Math.floor(Math.random() * length);
	}

	public static double[][] plus(double[][] a, double[][] b) {
		return plus2(a, b, 1);
	}

	public static double[][][] plus(double[][][] a, double[][][] b) {
		X.assertTrue(a.length == b.length);
		double[][][] res = new double[a.length][][];
		for (int i = 0; i < res.length; i++) {
			res[i] = plus(a[i], b[i]);
		}
		return res;
	}

	public static double[][] minus(double[][] a, double[][] b) {
		return plus2(a, b, -1);
	}

	public static double[][][] minus(double[][][] a, double[][][] b) {
		X.assertTrue(a.length == b.length);
		double[][][] res = new double[a.length][][];
		for (int i = 0; i < res.length; i++) {
			res[i] = minus(a[i], b[i]);
		}
		return res;
	}

	private static double[][] plus2(double[][] a, double[][] b, double lambda) {
		assertTrue(a.length == b.length);
		double[][] c = new double[a.length][];
		for (int i = 0; i < a.length; i++) {
			assertTrue(a[i].length == b[i].length);
			c[i] = new double[a[i].length];
			for (int j = 0; j < a[i].length; j++) {
				c[i][j] = a[i][j] + lambda * b[i][j];
			}
		}
		return c;
	}

	public static double manhattenNorm(double[] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++) {
			sum += Math.abs(d[i]);
		}
		return Math.sqrt(sum);
	}

	public static double euclideanNorm(double[] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++) {
			sum += Math.pow(d[i], 2);
		}
		return Math.sqrt(sum);
	}

	public static double norm(double[][] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++) {
			sum += Math.pow(euclideanNorm(d[i]), 2);
		}
		return Math.sqrt(sum);
	}

	public static double norm(double[][][] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++) {
			sum += Math.pow(norm(d[i]), 2);
		}
		return Math.sqrt(sum);
	}

	public static double[][] asDouble(int[][] M) {
		double[][] a = new double[M.length][M.length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				a[i][j] = (int) M[i][j];
			}
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<?> type, int size) {
		return (T[]) Array.newInstance(type, size);
	}

	public static double sum(double[] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++)
			sum += d[i];
		return sum;
	}

	public static double sum(double[][] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++)
			sum += sum(d[i]);
		return sum;
	}

	public static double sum(double[][][] d) {
		double sum = 0;
		for (int i = 0; i < d.length; i++)
			sum += sum(d[i]);
		return sum;
	}

	public static int sign(double d) {
		if (d < 0)
			return -1;
		if (d > 0)
			return 1;
		return 0;
	}

	public static double sampleMean(double[] ar) {
		if (null == ar || ar.length == 0)
			throw new RuntimeException("Array is null or empty!");
		double sum = 0;
		for (double d : ar)
			sum += d;
		return sum / ar.length;
	}

	public static boolean[] newBooleanArray(int length, boolean b) {
		boolean[] res = new boolean[length];
		X.fill(res, b);
		return res;
	}

	public static double[] newDoubleArray(int length, double d) {
		double[] res = new double[length];
		X.fill(res, d);
		return res;
	}

	public static int[] newIntArray(int length, int d) {
		int[] res = new int[length];
		X.fill(res, d);
		return res;
	}

	public static ArrayList<Integer> toList(int[] ar) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (Integer t : ar)
			res.add(t);
		return res;
	}

	public static <T> ArrayList<T> toList(T[] ar) {
		ArrayList<T> res = new ArrayList<T>();
		for (T t : ar)
			res.add(t);
		return res;
	}

	public static double[][] transpose(double[][] arr) {
		if (arr.length == 0)
			return new double[0][0];
		double[][] res = new double[arr[0].length][arr.length];
		for (int i = 0; i < arr.length; i++) {
			X.assertTrue(arr[i].length == arr[0].length,
					"all arrays must have the same size!");
			for (int j = 0; j < arr[i].length; j++) {
				res[j][i] = arr[i][j];
			}
		}
		return res;
	}

	public static double hypot(double a, double b) {
		return Math.sqrt(a * a + b * b);
	}

	/** sqrt(a^2 + b^2) without under/overflow. **/
	public static double hypot2(double a, double b) {

		double r;

		if (Math.abs(a) > Math.abs(b)) {

			r = b / a;

			r = Math.abs(a) * Math.sqrt(1 + r * r);

		} else if (b != 0) {

			r = a / b;

			r = Math.abs(b) * Math.sqrt(1 + r * r);

		} else {

			r = 0.0;

		}

		return r;

	}

	public static void fail(String desc) {
		throw new RuntimeException("Call to X.fail()-function: " + desc);
	}

	public static void fail() {
		throw new RuntimeException("Call to X.fail()-function.");
	}

	public static <T> T chooseRandomly(T[] arr) {
		return arr[X.randomIndex(arr)];
	}

	public static <T> T chooseRandomly(ArrayList<T> arr) {
		return arr.get(X.randomIndex(arr));
	}

	public static int[] randomPermutation(int size) {
		int[] res = X.nats(size);
		X.shuffle(res);
		return res;
	}

	public static void serialize(Object o, String file) {
		ObjectOutputStream objOut;
		if ((new File(file).exists())) {
			System.err.println("File already exists!");
			return;
		}

		try {
			objOut = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(file)));
			objOut.writeObject(o);
			objOut.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean fileExists(String file1) {
		return new File(file1).exists();
	}

	public static Object deserialize(String file) {
		Object obj = null;
		try {
			ObjectInputStream objIn = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(file)));
			obj = objIn.readObject();
			objIn.close();
		} catch (Exception e) {
			System.err.println("Deserialize() failed!");
			e.printStackTrace();
		}
		return obj;
	}

	public static void throwE() {
		throw new RuntimeException();
	}

	public static void throwE(String s) {
		throw new RuntimeException(s);
	}

	public static void throwE(Exception e) {
		throw new RuntimeException(e);
	}

	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
