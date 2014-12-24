package ranges.action.dta;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import tools.Tools;

public class JUnitTestMyDataStructure {
  @SuppressWarnings("null")
  MyDataStructure<Integer> dta;
  private static final Predicate<Integer> predicate = x -> x % 2 == 0;

  @Test
  public void testRest1() {
    List<Integer> source = Tools.asList(1, 2, 3, 4, 5, 6, 7);
    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 3);

    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }

    Predicate<Integer> p2 = x -> x < 5;

    dta.nextPredicate(p2);

    assertTrue(dta.currentSize() == 2);
    for (Integer i : dta) {
      assertTrue(p2.test(i));
    }

    Predicate<Integer> p3 = x -> x < 3;
    dta.nextPredicate(p3);

    assertTrue(dta.currentSize() == 1);
    for (Integer i : dta) {
      assertTrue(p3.test(i));
    }

    Predicate<Integer> p4 = x -> x > 3;
    dta.nextPredicate(p4);

    assertTrue(dta.currentSize() == 0);
    for (Integer i : dta) {
      assertTrue(p4.test(i));
    }

    Predicate<Integer> p5 = x -> x % 3 == 0;
    dta.reset(7);
    dta.nextPredicate(p5);

    assertTrue(dta.currentSize() == 2);
    for (Integer i : dta) {
      assertTrue(p5.test(i));
    }

  }

  @Test
  public void test() {
    List<Integer> source = Tools.asList();

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 0);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }
  }

  @Test
  public void test2() {
    List<Integer> source = Tools.asList(1);

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 0);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }
  }

  @Test
  public void test3() {
    List<Integer> source = Tools.asList(0);

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 1);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }
  }

  @Test
  public void test4() {
    List<Integer> source = Tools.asList(1, 2);

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 1);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }
  }

  @Test
  public void test5() {
    List<Integer> source = Tools.asList(2, 1);

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 1);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }
  }

  @Test
  public void test6() {
    List<Integer> source = Tools.asList(2, 4);

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 2);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }
  }

  @Test
  public void test7() {
    List<Integer> source = Tools.asList(17, 15);

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 0);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }
  }

  @Test
  public void test30() {
    List<Integer> source = Tools.asList(1, 2, 3, 4, 5);

    dta = new MyDataStructure<Integer>(source);
    dta.nextPredicate(predicate);

    assertTrue(dta.currentSize() == 2);
    for (Integer i : dta) {
      assertTrue(predicate.test(i));
    }

    dta.print();

  }
}
