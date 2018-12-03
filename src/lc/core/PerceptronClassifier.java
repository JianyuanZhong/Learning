package lc.core;

import math.util.VectorOps;

public class PerceptronClassifier extends LinearClassifier {
	
	public PerceptronClassifier(int ninputs) {
		super(ninputs);
	}
	
	/**
	 * A PerceptronClassifier uses the perceptron learning rule
	 * (AIMA Eq. 18.7): w_i \leftarrow w_i+\alpha(y-h_w(x)) \times x_i 
	 */
	public void update(double[] x, double y, double alpha) {
	    // Must be implemented by you
		while(y - eval(x) != 0) {

			for (int i = 0; i < weights.length; i++) {
				weights[i] = weights[i] + alpha * (y - eval(x)) * x[i];
			}

		}
	}
	
	/**
	 * A PerceptronClassifier uses a hard 0/1 threshold.
	 */
	public double threshold(double z) {
	    // Must be implemented by you
		if(z < 0.0)
			return 0;
		else
			return 1;
	}
	
}
