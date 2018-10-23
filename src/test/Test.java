package test;

import main.Trilateration;

public class Test {

	public static void main(String[] args) {
//		double[][] points = new double[][] {{30, 68}, 
//			{50, -6}, {110, -20}, {35, 15}, {45, 97}};
//		double[] distances = new double[] {70, 70, 70, 70, 70};
//		double[] startPoint = new double[] {100, 50};
		
		// test case 1
		// double[][] points = new double[][] {{0, 0}, {0, 60}, {80, 0}, {80, 60}};
		// double[] distances = new double[] {72.11, 63.25, 44.72, 28.28};
		
		// test case 2
		// double[][] points = new double[][]{{1.0, 1.0}, {3.0, 1.0}, {2.0, 2.0}};
        // double[] distances = new double[]{1.0, 1.0, 1.0};
		
		// test case 3
//		double[][] points = new double[][]{{5.0, -6.0}, {13.0, -15.0}, {21.0, -3.0}};
//        double[] distances = new double[]{8.06, 13.97, 23.32};
        
        // test case 4
//        double[][] points = new double[][]{{5.0, -6.0}, {13.0, -15.0}, {21.0, -3.0}, {12.42, -21.2}};
//        double[] distances = new double[]{8.06, 13.97, 23.32, 15.31};
        
        // test case 5
//        double[][] points = new double[][]{{1.0, 1.0}, {3.0, 1.0}, {2.0, 2.0}};
//        double[] distances = new double[]{0.5, 0.5, 0.5};
		
		// test case 6
//		double[][] points = new double[][]{{1.0, 1.0}, {1.0, 1.0}, {3.0, 1.0}};
//        double[] distances = new double[]{1.0, 1.0, 1.0};
        
		// test case 7, this degenerate example throws an error 
		double[][] points = new double[][]{{1.0, 1.0}, {1.0, 1.0}, {1.0, 1.0}};
        double[] distances = new double[]{1.0, 1.0, 1.0};
		
		try {
			Trilateration trilateration = new Trilateration(points,  distances);
			double[] result = trilateration.calculate();
			System.out.println("Result: (" + result[0] + ", " + result[1] + ")");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
