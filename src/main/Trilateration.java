/*
 * Reference: http://commons.apache.org/proper/commons-math/userguide/leastsquares.html
 * */

package main;

import java.util.ArrayList;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

public class Trilateration {
	
	double[] distances;
	Vector2D[] points;
	
	public Trilateration(double[][] points, double[] distances) {
		final double epsilon = 1e-7;
		// check if input is legal
		if(points.length < 2) {
			throw new IllegalArgumentException("At least two points needed.");
		}
		if(points.length != distances.length) {
			throw new IllegalArgumentException("The number of points (" + points.length + ") should match the number of distances (" + distances.length + ").");
		}
		ArrayList<Vector2D> list = new ArrayList<>();
		for (int i = 0; i < points.length; ++i) {
			if (points[i].length != 2) {
				throw new IllegalArgumentException("The dimension of point " + i + " should be 2.");
			}
			list.add(new Vector2D(points[i]));
		}
		// bound distances to strictly positive domain
		for (int i = 0; i < distances.length; i++) {
			distances[i] = Math.max(distances[i], epsilon);
		}
		
		this.distances = distances.clone();		
		this.points = list.toArray(new Vector2D[points.length]);
	}
	
	public double[] calculate() {
		
		// Jacobian
		MultivariateJacobianFunction distancesToCurrentCenter = new MultivariateJacobianFunction() {
		    public Pair<RealVector, RealMatrix> value(final RealVector point) {

		        Vector2D center = new Vector2D(point.getEntry(0), point.getEntry(1));

		        RealVector value = new ArrayRealVector(points.length);
		        RealMatrix jacobian = new Array2DRowRealMatrix(points.length, 2);

		        for (int i = 0; i < points.length; ++i) {
		            Vector2D o = points[i];
		            double modelI = Vector2D.distance(o, center);
		            value.setEntry(i, modelI);
		            // derivative with respect to p0 = x center
		            jacobian.setEntry(i, 0, (center.getX() - o.getX()) / modelI);
		            // derivative with respect to p1 = y center
		            jacobian.setEntry(i, 1, (center.getY() - o.getY()) / modelI);
		        }

		        return new Pair<RealVector, RealMatrix>(value, jacobian);

		    }
		};
		
		// calculate start point
		// start point is the average of all reference points
		double[] startPoint = new double[] {0, 0};
		for (int i = 0, len = points.length; i < len; ++i) {
			startPoint[0] += points[i].getX();
			startPoint[1] += points[i].getY();
		}
		startPoint[0] /= points.length;
		startPoint[1] /= points.length;
		
		// least squares problem
		LeastSquaresProblem problem = new LeastSquaresBuilder().
                start(startPoint).
                model(distancesToCurrentCenter).
                target(distances).
                lazyEvaluation(false).
                maxEvaluations(1000).
                maxIterations(1000).
                build();
		
		// optimize
		LeastSquaresOptimizer.Optimum optimum = new LevenbergMarquardtOptimizer().optimize(problem);
		double[] estPoint = new double[] {optimum.getPoint().getEntry(0), optimum.getPoint().getEntry(1)};
		return estPoint;
		
	}
	
}
