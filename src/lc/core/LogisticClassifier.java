package lc.core;

import math.util.VectorOps;

public class LogisticClassifier extends LinearClassifier {
	
	public LogisticClassifier(int ninputs) {
		super(ninputs);
	}
	
	/**
	 * A LogisticClassifier uses the logistic update rule
	 * (AIMA Eq. 18.8): w_i \leftarrow w_i+\alpha(y-h_w(x)) \times h_w(x)(1-h_w(x)) \times x_i 
	 */
	public void update(double[] x, double y, double alpha) {
	    // Must be implemented by you
		while (Math.abs(y - eval(x)) >= 0.001){
			for(int i = 0; i < weights.length; i++){
				double h_w = threshold(VectorOps.dot(weights, x));
				weights[i] = weights[i] + alpha * (y - h_w) * h_w * (1 - h_w) * x[i];
			}
		}
	}
	
	/**
	 * A LogisticClassifier uses a 0/1 sigmoid threshold at z=0.
	 */
	public double threshold(double z) {
	    // Must be implemented by you
		return 1 / (1 + Math.exp(-z));
	}

}
