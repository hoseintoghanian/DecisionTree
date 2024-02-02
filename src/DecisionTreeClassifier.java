import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionTreeClassifier {
    public Tree tree;
    public int minSamplesSplit;
    public int depth;
    public float[][] feature;
    public float[] label;

    public DecisionTreeClassifier(Tree tree, float[][] feature, float[] label) {
        this.tree = tree;
        this.feature = feature;
        this.label = label;
    }

//    public float[] predict(float[][] X) {
//        float[] predictions = new float[X.length];
//        for (int i = 0; i < X.length; i++) {
//            predictions[i] = makePrediction(X[i], tree.root);
//        }
//        return predictions;
//    }

//    public float makePrediction(float[] x, Tree tree) {
//        if (tree.getValue() != null)
//            return tree.getValue();
//        float featureVal = x[tree.getFeatureIndex()];
//        if (featureVal <= tree.getThreshold()) {
//            return makePrediction(x, tree.getLeft());
//        } else {
//            return makePrediction(x, tree.getRight());
//        }
//    }

    public Node buildTree(float[][] feature, int currDepth, int maxDepth, ArrayList featureIndexArray) {
        Map<String, Object> bestSplit = getBestSplit(feature, feature.length, feature[0].length, label);
        int featureIndex = (int) bestSplit.get("feature_index");
        featureIndexArray.remove(featureIndex);
        float infoGain = (float) bestSplit.get("info_gain");
        Node parent = (Node) bestSplit.get("parent_node");
//        float[][] dataset1 = new float[feature.length][feature[0].length];
//        if (bestSplit.size() > 0)
//            dataset1 = (float[][]) bestSplit.get("child_dataset1");
//        float[][] dataset2 = new float[feature.length][feature[0].length];
//        if (bestSplit.size() > 1)
//            dataset2 = (float[][]) bestSplit.get("child_dataset2");
//        float[][] dataset3 = new float[feature.length][feature[0].length];
//        if (bestSplit.size() > 2)
//            dataset3 = (float[][]) bestSplit.get("child_dataset3");
//        float[][] dataset4 = new float[feature.length][feature[0].length];
//        if (bestSplit.size() > 3)
//            dataset4 = (float[][]) bestSplit.get("child_dataset4");
//        float[][] dataset5 = new float[feature.length][feature[0].length];
//        if (bestSplit.size() > 4)
//            dataset5 = (float[][]) bestSplit.get("child_dataset5");
        // Check conditions for building subtrees
        if (infoGain > 0 && currDepth <= maxDepth) {
            for (int i = 0; i < parent.getChildrenNodes().size(); i++) {
                Node subTree;
                if (!parent.getChildrenByIndex(i).isLeaf)
                    subTree = buildTree((float[][]) bestSplit.get(String.format("child_dataset%d", i + 1)), currDepth + 1, maxDepth, featureIndexArray);
            }
            return new Node(parent.getValue(), false);
        }
        return new Node((float[]) bestSplit.get("value"), true);
//        float[][] X = new float[feature.length][feature[0].length - 1];
//        float[] Y = new float[feature.length];
//        for (int i = 0; i < feature.length; i++) {
//            System.arraycopy(feature[i], 0, X[i], 0, feature[i].length - 1);
//            Y[i] = feature[i][feature[i].length - 1];
//        }
//        int numSamples = X.length;
//        int numFeatures = X[0].length + 1;
//        if (numSamples >= minSamplesSplit && currDepth <= maxDepth) {
//            Map<String, Object> bestSplit = getBestSplit(feature, numSamples, numFeatures);
//
//            if ((float) bestSplit.get("info_gain") > 0) {
//                Node leftSubtree = buildTree((float[][]) bestSplit.get("dataset_left"), currDepth + 1,
//                        minSamplesSplit, maxDepth);
//                Node rightSubtree = buildTree((float[][]) bestSplit.get("dataset_right"), currDepth + 1,
//                        minSamplesSplit, maxDepth);
//
//                return new Node((int) bestSplit.get("threshold"), (float) bestSplit.get("info_gain"), (int) bestSplit.get("feature_index"));
//            }
//        }
//        float[] leafValue = calculateLeafValue(Y);
    }

    private boolean isLeaf(float[][] splitResult, int featureIndex) {
        float equals = splitResult[0][17];
        for (int j = 0; j < splitResult.length; j++) {
            if (splitResult[j][17] != equals)
                return false;
        }
        return true;
    }

    public Map<String, Object> getBestSplit(float[][] dataset, int numSamples, int numFeatures, float[] label) {
        Map<String, Object> bestSplit = new HashMap<>();
        float maxInfoGain = Float.NEGATIVE_INFINITY;

        for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
            float[] featureValues = new float[numSamples];
            for (int i = 0; i < dataset.length - 1; i++) {
                featureValues[i] = dataset[i][featureIndex];
            }

//            float[] uniqueFeatureValues = new float[17];
            // Adding unique elements to uniqueFeatureValues
//            for (int i = 0; i < featureValues.length; i++) {
//                uniqueFeatureValues = Arrays.stream(featureValues).distinct().toArray();
//            }
            // Sort the uniqueFeatureValues array
//            Arrays.sort(uniqueFeatureValues);
//            float[] possibleThresholds = calculateThValues(uniqueFeatureValues);
            float[] parentValues = new float[numSamples];
//            boolean flag = false;
            for (int i = 0; i < numSamples; i++) {
                parentValues[i] = dataset[i][featureIndex];
//                if (parentValues[i] != parentValues[0])
//                    flag = true;
            }
            List<float[][]> splitResult = split(dataset, label, featureIndex, featureValues, numFeatures);

            //add each child node to its parent
            Node parent = new Node(parentValues, false);
            buildChildren(splitResult, parent, featureIndex, numSamples);
            float currInfoGain = maxInfoGain;
//            System.out.println("flag = " + flag);
//            if (flag)
            currInfoGain = tree.informationGain(parent);
            System.out.println("currInfoGain = " + currInfoGain);
            System.out.println("featureIndex = " + featureIndex);
            if (currInfoGain > maxInfoGain) {
                bestSplit.put("feature_index", featureIndex);
                bestSplit.put("parent_node", parent);
                bestSplit.put("child_dataset1", splitResult.get(0));
                if (splitResult.size() > 1)
                    bestSplit.put("child_dataset2", splitResult.get(1));
                if (splitResult.size() > 2)
                    bestSplit.put("child_dataset3", splitResult.get(2));
                if (splitResult.size() > 3)
                    bestSplit.put("child_dataset4", splitResult.get(3));
                if (splitResult.size() > 4)
                    bestSplit.put("child_dataset5", splitResult.get(4));
                if (splitResult.size() > 5)
                    bestSplit.put("child_dataset6", splitResult.get(5));
                bestSplit.put("info_gain", currInfoGain);
                maxInfoGain = currInfoGain;
            }
        }
        return bestSplit;
    }

    private void buildChildren(List<float[][]> splitResult, Node parent, int featureIndex, int numSamples) {
        if (!splitResult.isEmpty()) {
            for (int i = 0; i < splitResult.size(); i++) {
                Node childNode;
                if (splitResult.get(i) != null) {
                    float[] childValues = new float[numSamples];
                    for (int j = 0; j < splitResult.get(i).length; j++) {
                        childValues[j] = splitResult.get(i)[j][featureIndex];
                    }
                    if (isLeaf(splitResult.get(i), featureIndex)) {
                        //childNode is a leaf Node
                        childNode = new Node(childValues, true);
                        System.out.println("Gorgali leaf");
                    } else {
                        //childNode is a Decision Node
                        childNode = new Node(childValues, false);
                        System.out.println("Gorgali Decision Node");
                    }
                    parent.addChild(childNode);
                }
            }
        }
    }

//        public void fit(float[][] X, float[] Y) {
//        float[][] dataset = new float[X.length][X[0].length + 1];
//        for (int i = 0; i < X.length; i++) {
//            System.arraycopy(X[i], 0, dataset[i], 0, X[i].length);
//            dataset[i][X[i].length] = Y[i];
//        }
//
//        root = buildTree(dataset, 0);
//    }
//    public float[] predict(float[][] X) {
//        float[] predictions = new float[X.length];
//        for (int i = 0; i < X.length; i++) {
//            predictions[i] = makePrediction(X[i], root);
//        }
//        return predictions;
//    }
//
//    public float makePrediction(float[] x, Node tree) {
//        if (tree.getValue() != null) {
//            return tree.getValue();
//        }
//
//        float featureVal = x[tree.getFeatureIndex()];
//        if (featureVal <= tree.getThreshold()) {
//            return makePrediction(x, tree.getLeft());
//        } else {
//            return makePrediction(x, tree.getRight());
//        }
//    }
//    private float[] calculateThValues(float[] featureValues) {
//        float[] possibleThresholds = new float[7];
//        // Select 3 items randomly from the selected items
//        if (featureValues.length >= 2 && featureValues.length <= 3) {
//            possibleThresholds[0] = featureValues[0];
//            possibleThresholds[1] = featureValues[1];
//        } else if (featureValues.length >= 4 && featureValues.length <= 5) {
//            possibleThresholds[0] = featureValues[0];
//            possibleThresholds[1] = featureValues[2];
//            possibleThresholds[2] = featureValues[4];
//        } else if (featureValues.length >= 6) {
//            possibleThresholds[0] = featureValues[0];
//            possibleThresholds[1] = featureValues[2];
//            possibleThresholds[2] = featureValues[4];
//            possibleThresholds[3] = featureValues[5];
//        }
//        return possibleThresholds;
//    }
//
//    private void nodeAdder(float[] dataset, Node parent) {
//        if (dataset != null && dataset.length > 0) {
//            Node childNode = new Node(dataset);
//            parent.addChild(childNode);
//        }
//    }

    public List<float[][]> split(float[][] dataset, float[] label, int featureIndex, float[] thresholdValues, int numFeatures) {
        List<float[]> datasetList1 = new ArrayList<>();
        List<float[]> datasetList2 = new ArrayList<>();
        List<float[]> datasetList3 = new ArrayList<>();
        List<float[]> datasetList4 = new ArrayList<>();
        for (int i = 0; i < dataset.length; i++) {
            float[] temp = new float[numFeatures + 1];
            System.arraycopy(dataset[i], 0, temp, 0, numFeatures);
            temp[numFeatures] = label[i];
            if (dataset[i][featureIndex] <= thresholdValues[0]) {
                datasetList1.add(temp);
            } else if (dataset[i][featureIndex] > thresholdValues[0] && dataset[i][featureIndex] <= thresholdValues[1]) {
                datasetList2.add(temp);
            } else if (dataset[i][featureIndex] > thresholdValues[1] && dataset[i][featureIndex] <= thresholdValues[2]) {
                datasetList3.add(temp);
            } else if (dataset[i][featureIndex] > thresholdValues[2] && dataset[i][featureIndex] <= thresholdValues[3]) {
                datasetList4.add(temp);
            }
        }
        List<float[][]> datasetListResult = new ArrayList<>();
        pourList(datasetListResult, datasetList1, numFeatures);
        pourList(datasetListResult, datasetList2, numFeatures);
        pourList(datasetListResult, datasetList3, numFeatures);
        pourList(datasetListResult, datasetList4, numFeatures);
        return datasetListResult;
    }

    private void pourList(List<float[][]> datasetListResult, List<float[]> datasetlist, int numFeatures) {
        if (!datasetlist.isEmpty()) {
            float[][] temp = new float[datasetlist.size()][numFeatures + 1];
            for (int j = 0; j < datasetlist.size(); j++) {
                for (int l = 0; l < numFeatures + 1; l++) {
                    temp[j][l] = datasetlist.get(j)[l];
                    System.out.print(" " + temp[j][l] + " ");
                }
                System.out.println();
            }
            datasetListResult.add(temp);
        }
    }

//    public void printTree(Node tree, String indent) {
//        if (tree.getValue() != null) {
//            System.out.println(tree.getValue());
//        } else {
//            System.out.println("X_" + tree.getFeatureIndex() + " <= " + tree.getThreshold() + " ? " + tree.getInfoGain());
//            System.out.print(indent + "left: ");
//            printTree(tree.getLeft(), indent + "  ");
//            System.out.print(indent + "right: ");
//            printTree(tree.getRight(), indent + "  ");
//        }
//    }

//    public void fit(float[][] X, float[] Y) {
//        float[][] dataset = new float[X.length][X[0].length + 1];
//        for (int i = 0; i < X.length; i++) {
//            System.arraycopy(X[i], 0, dataset[i], 0, X[i].length);
//            dataset[i][X[i].length] = Y[i];
//        }
//
//        root = buildTree(dataset, 0);
//    }

//        public float[] predictAll(float[][] data, int depth) {
//        return new float[0];
//    }

//    public float[] calculateLeafValue(float[] Y) {
//        Map<Float, Integer> counts = new HashMap<>();
//        for (float value : Y) {
//            counts.put(value, counts.getOrDefault(value, 0) + 1);
//        }
//        float maxCount = Float.NEGATIVE_INFINITY;
//        float leafValue = 0;
//        for (Map.Entry<Float, Integer> entry : counts.entrySet()) {
//            if (entry.getValue() > maxCount) {
//                maxCount = entry.getValue();
//                leafValue = entry.getKey();
//            }
//        }
//        return new float[]{leafValue};
//    }
//
//    public float accuracyScore(float[] correctLabels, float[] predictedLabels) {
//        int correctPredicted = 0;
//        for (int i = 0; i < correctLabels.length; i++) {
//            if (correctLabels[i] == predictedLabels[i])
//                correctPredicted++;
//        }
//        return (float) ((float) correctPredicted / correctLabels.length * 100.0);
//    }
}