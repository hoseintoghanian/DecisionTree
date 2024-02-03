import java.util.*;

public class DTreeClassifier {
    public Tree tree = new Tree(Tree.root);
    public float[][] featureTrain;
    public float[] labelTrain;

    public DTreeClassifier(float[][] featureTrain, float[] labelTrain) {
        this.featureTrain = featureTrain;
        this.labelTrain = labelTrain;
    }

    public float predict(float[][] featureTest, Node node, int featureValue, ArrayList<Integer> featureArr) {
        //if node is a Leaf Node
        if (node.isLeaf)
            return featureTest[featureValue][featureTest[0].length - 1];
            //if node is Decision Node
        else {
            // Remove the featureValue from featureArr
            tree.deleteFeature(featureArr, featureValue);
            Iterator<Integer> iterator = featureArr.iterator();
            for (Node child : node.childrenNodes) {
                makePrediction(featureTest, new Tree(node), featureArr);
            }
            return 0;
        }
    }

    //Takes featureTest and tree and iterates over parent's child to until reach a leaf
    public float[] makePrediction(float[][] featureTest, Tree tree, ArrayList<Integer> featureArr) {
        Node root = tree.root;
        float predictedLabel;
        float[] predictedArray = new float[featureTest[0].length];
        for (int i = 0; i < featureTest[0].length; i++) {
            for (Node child : root.childrenNodes) {
                int featureValue = (int) featureTest[i][root.featureIndex];
//                if (featureArrr.get(root.getFeatureIndex()) == featureValue) {
                if (containValue(child, featureValue)) {
                    predictedLabel = predict(featureTest, child, featureValue, featureArr);
                    if (predictedLabel != 0)
                        predictedArray[i] = predictedLabel;
                }
//                }
            }
        }
        return predictedArray;
    }

    //checks to see if a given Value is on the child 's values or not
    private boolean containValue(Node child, int featureValue) {
        for (float childValue : child.value) {
            if (childValue == featureValue)
                return true;
        }
        return false;
    }

    public float accuracy(int[] correctLabels, float[] predictedLabels) {
        int correctPredicted = 0;
        for (int i = 0; i < correctLabels.length; i++) {
            if (correctLabels[i] == predictedLabels[i])
                correctPredicted++;
        }
        System.out.println("correct predicted = " + correctPredicted);
        System.out.println("correctLabels.length = " + correctLabels.length);
        return (float) correctPredicted / correctLabels.length;
    }
}