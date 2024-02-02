import java.util.HashMap;
import java.util.Map;

public class Tree {
    int depth;
    int featureIndex;
    int threshold;
    Node root;

    public Tree(int featureIndex, int threshold) {
        this.featureIndex = featureIndex;
        this.threshold = threshold;
    }

    public Tree(Node root) {
        this.root = root;
    }

    //Calculate information gain for every child of the parent node
    public float informationGain(Node parentNode) {
        float[] weight = new float[parentNode.getChildrenNodes().size()];
        float sumEntropies = 0;
        float[] childValues;
        float gain;
        for (int i = 0; i < parentNode.getChildrenNodes().size(); i++) {
            childValues = parentNode.getChildrenNodes().get(i).getValue();
            weight[i] = calculateChildWeight(childValues, parentNode.getValue());
            sumEntropies += weight[i] * entropy(childValues);
        }
        if (sumEntropies == 0)
            System.out.println("this Node is leaf Node");
        gain = entropy(parentNode.getValue()) - sumEntropies;
        parentNode.setInfoGain(gain);
        return gain;
    }

    public float calculateChildWeight(float[] childValue, float[] parentValue) {
        return (float) childValue.length / parentValue.length;
    }

    //calculate entropy for a set of numbers
    public float entropy(float[] label) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int sum = 0;
        for (float value : label) {
            countMap.put((int) value, countMap.getOrDefault(value, 0) + 1);
            sum++;
        }
        float entropy = 0;
        for (float count : countMap.values()) {
            float probability = count / sum;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        return entropy;
    }

//    public float giniIndex(float[] y) {
//        float[] classLabels = Arrays.stream(y).distinct().toArray();
//        float gini = 0;
//
//        for (float cls : classLabels) {
//            float pCls = (float) Arrays.stream(y).filter(value -> value == cls).count() / y.length;
//            gini += Math.pow(pCls, 2);
//        }
//
//        return 1 - gini;
//    }
}