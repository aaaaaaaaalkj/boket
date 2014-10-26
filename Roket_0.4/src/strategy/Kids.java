package strategy;

import java.util.Random;

public class Kids {
	static Random r = new Random();

	public static enum Child {
		Son, Dauther;
	}

	public static class Family {
		Child child1;
		Child child2;

		Family() {
			this.child1 = r.nextBoolean() ? Child.Son : Child.Dauther;
			this.child2 = r.nextBoolean() ? Child.Son : Child.Dauther;
		}

		Child getFirst() {
			return child1;
		}

		Child getSecond() {
			return child1;
		}

		boolean twoSons() {
			return child1 == Child.Son && child2 == Child.Son;
		}

		boolean hasTwoDauthers() {
			return child1 == Child.Dauther && child2 == Child.Dauther;
		}

		boolean olderDauther() {
			return child2 == Child.Dauther;
		}

		boolean youngerDauther() {
			return child1 == Child.Dauther;
		}

	}

	public static void main(String[] args) {

		int countSuitableFamilies = 0;
		int countSuitableFamilies2 = 0;

		int countTwoDauthers = 0;
		int countTwoDauthers2 = 0;

		for (int i = 0; i < 10000; i++) {
			Family family = new Family();

			if (!family.twoSons()) {
				countSuitableFamilies++;

				if (family.youngerDauther()) {
					countSuitableFamilies2++;
					if (family.hasTwoDauthers()) {
						countTwoDauthers2++;
					}
				}

				if (family.hasTwoDauthers()) {
					countTwoDauthers++;
				}
			}

		}
		double frac2 = ((double) countTwoDauthers2) / countSuitableFamilies2;
		System.out.println("fraction two daugthers (older daughter): " + frac2);

		double frac = ((double) countTwoDauthers) / countSuitableFamilies;
		System.out.println("fraction two daugthers: " + frac);

	}
}
