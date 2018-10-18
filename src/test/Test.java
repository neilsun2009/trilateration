package test;

import main.Trilateration;

public class Test {

	public static void main(String[] args) {
//		double[][] points = new double[][] {{30, 68}, 
//			{50, -6}, {110, -20}, {35, 15}, {45, 97}};
//		double[] distances = new double[] {70, 70, 70, 70, 70};
//		double[] startPoint = new double[] {100, 50};
		double[][] points = new double[][] {{0, 0}, {0, 60}, {80, 0}, {80, 60}};
		double[] distances = new double[] {72.11, 63.25, 44.72, 28.28};
		double[] startPoint = new double[] {30, 20};
		Trilateration trilateration = new Trilateration(points,  distances);
		double[] result = trilateration.calculate(startPoint);
		System.out.println("Result: (" + result[0] + ", " + result[1] + ")");
	}

}
