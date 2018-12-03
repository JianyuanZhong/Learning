package dt.core;

import java.util.*;

import dt.util.ArraySet;

/**
 * Implementation of the decision-tree learning algorithm in AIMA Fig 18.5.
 * This is based on ID3 (AIMA p. 758).
 */
public class DecisionTreeLearner extends AbstractDecisionTreeLearner {
	
	/**
	 * Construct and return a new DecisionTreeLearner for the given Problem.
	 */
	public DecisionTreeLearner(Problem problem) {
		super(problem);
	}
	
	/**
	 * Main recursive decision-tree learning (ID3) method.  
	 */
	@Override
	protected DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parent_examples) {
	    // Must be implemented by you; the following two methods may be useful
		if(examples.isEmpty()) return new DecisionTree(pluralityValue(parent_examples));
		else if (allSameClassification(examples)){
			String classification = examples.iterator().next().getOutputValue();
			return new DecisionTree(classification);
		}else {
			Variable A = mostImportantVariable(attributes, examples);
			DecisionTree tree = new DecisionTree(A);
			for(String v_k : A.domain){
				Set<Example> exs = examplesWithValueForAttribute(examples, A, v_k);
				List<Variable> attriWithoutA = new ArrayList<>(attributes);
				attriWithoutA.remove(A);
				DecisionTree subtree = learn(exs, attriWithoutA, examples);
				tree.children.add(subtree);
			}
			return tree;
		}

	}

	public boolean allSameClassification(Set<Example> examples){
		String classification = null;
		for(Example ex : examples){
			if(classification == null) classification = ex.getOutputValue();
			if(!ex.getOutputValue().equals(classification)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the most common output value among a set of Examples,
	 * breaking ties randomly.
	 * I don't do the random part yet.
	 */
	@Override
	protected String pluralityValue(Set<Example> examples) {
	    // Must be implemented by you
		HashMap<String, Integer> countTable = new HashMap<>();
		for(Example ex : examples){
			if(countTable.containsKey(ex.outputValue)){
				countTable.put(ex.outputValue, countTable.get(ex.outputValue) + 1);
			}else {
				countTable.put(ex.outputValue, 1);
			}
		}

		int maxCount = -1;
		ArrayList<String> mostCommonClass = new ArrayList<>();
		for(String classification : countTable.keySet()){
			if(countTable.get(classification) > maxCount){
				maxCount = countTable.get(classification);
				mostCommonClass = new ArrayList<>();
				mostCommonClass.add(classification);
			}else if(countTable.get(classification) == maxCount){
				mostCommonClass.add(classification);
			}

		}

		if(mostCommonClass.size() == 1){
			return mostCommonClass.get(0);
		}else {
			int rand = new Random().nextInt(mostCommonClass.size());
			return mostCommonClass.get(rand);
		}
	}
	
	/**
	 * Returns the single unique output value among the given examples
	 * is there is only one, otherwise null.
	 */
	@Override
	protected String uniqueOutputValue(Set<Example> examples) {
	    // Must be implemented by you
		boolean isSingleUniqueVal = false;
		String singleUniqueVal = null;
		HashMap<String, Integer> countTable = new HashMap<>();
		for(Example ex : examples){
			if(countTable.containsKey(ex.outputValue)){
				countTable.put(ex.outputValue, countTable.get(ex.outputValue) + 1);
			}else {
				countTable.put(ex.outputValue, 1);
			}
		}

		for(String classifation : countTable.keySet()){
			if(countTable.get(classifation) == 1){
				if(isSingleUniqueVal) return null;
				else {
					isSingleUniqueVal = true;
					singleUniqueVal = classifation;
				}
			}
		}

		if (isSingleUniqueVal) {
			return singleUniqueVal;
		}

		return null;
	}
	
	//
	// Utility methods required by the AbstractDecisionTreeLearner
	//

	/**
	 * Return the subset of the given examples for which Variable a has value vk.
	 */
	@Override
	protected Set<Example> examplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
	    // Must be implemented by you
		Set<Example> exs = new HashSet<>();
		for(Example ex : examples){
			if(ex.inputValues.get(a).equals(vk)) exs.add(ex);
		}
		return exs;
	}
	
	/**
	 * Return the number of the given examples for which Variable a has value vk.
	 */
	@Override
	protected int countExamplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
		int result = 0;
		for (Example e : examples) {
			if (e.getInputValue(a).equals(vk)) {
				result += 1;
			}
		}
		return result;
		
	}

	/**
	 * Return the number of the given examples for which the output has value vk.
	 */
	@Override
	protected int countExamplesWithValueForOutput(Set<Example> examples, String vk) {
	    // Must be implemented by you
		int count = 0;
		for (Example ex : examples){
			if(ex.getOutputValue().equals(vk))
				count++;
		}
		return count;
	}

}
