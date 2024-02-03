import java.util.*;

public class Tree {
    int depth;
    int featureIndex;
    int threshold;
    public static Node root;
    float[] label;


    public Tree(int featureIndex) {
        this.featureIndex = featureIndex;
    }

    public Tree(Node root) {
        this.root = root;
    }
    public Node createTree(float[][] dataset, int currDepth, ArrayList<Integer> featureIndexArray) {
        // Split the dataset
        Map<String, Object> bestSplit = getBestSplit(dataset, dataset.length, dataset[0].length, label, featureIndexArray);
        // Extract information from the best split
        int featureIndex = (int) bestSplit.get("feature_index");
        float infoGain = (float) bestSplit.get("info_gain");
        Node parent = (Node) bestSplit.get("parent_node");
        // Remove the bestSplit's index from featureIndexArray
        deleteFeature(featureIndexArray, featureIndex);
        System.out.println("currDepth = " + currDepth);
        System.out.println("currinfoGain = " + infoGain);
        // Check conditions for building subtrees
        for (int i = 0; i < parent.childrenNodes.size(); i++) {
            Node subTree;
            if (calculateLeafValue((float[][]) bestSplit.get(String.format("child_dataset%d", i + 1))) <= 0.8) {
                //whether child Node is a Decision Node or Leaf Node
                if (!parent.getChildrenByIndex(i).isLeaf) {
                    System.out.println("GorgAliiiiiiiiiiii");
                    subTree = createTree((float[][]) bestSplit.get(String.format("child_dataset%d", i + 1)), currDepth + 1, featureIndexArray);
                }
                // Return a non-leaf node
                return new Node(parent.value, false);
            }
        }
        // Return a Leaf Node
        return new Node((float[]) bestSplit.get("value"), true);
    }

    //check for leaf or decision Node
    private boolean checkLeaf(float[][] splitResult) {
        float equals = splitResult[0][17];
        for (int j = 0; j < splitResult.length; j++) {
            if (splitResult[j][17] != equals) {
                return false;
            }
        }
        return true;
    }

    //calculates Possible threshold and find the best split for a given dataset
    public Map<String, Object> getBestSplit(float[][] dataset, int numSamples, int numFeatures, float[] labels, ArrayList<Integer> featureArr) {
        Map<String, Object> bestSplit = new HashMap<>();
        float maxInfoGain = Float.NEGATIVE_INFINITY;

        for (int featureIndex : featureArr) {
            float[] featureValues = new float[numSamples];
            for (int i = 0; i < dataset.length - 1; i++) {
                featureValues[i] = dataset[i][featureIndex];
            }
            float[] uniqueFeatureValues;
            //Adding unique elements to uniqueFeatureValues
            uniqueFeatureValues = Arrays.stream(featureValues).distinct().toArray();
            //Sort the uniqueFeatureValues array
            Arrays.sort(uniqueFeatureValues);
            float[] possibleThArr = new float[9];
            System.arraycopy(uniqueFeatureValues, 0, possibleThArr, 0, uniqueFeatureValues.length);
            for (int i = uniqueFeatureValues.length; i < 9; i++) {
                possibleThArr[i] = -1;
            }
            float[] parentValues = new float[numSamples];
            for (int i = 0; i < numSamples; i++) {
                parentValues[i] = dataset[i][featureIndex];
            }
            List<float[][]> splitResult = split(dataset, labels, featureIndex, possibleThArr, numFeatures);

            //add each child node to its parent
            Node parent = new Node(parentValues, false);
            buildChildren(splitResult, parent, featureIndex, numSamples);
            float currInfoGain = maxInfoGain;
            currInfoGain = iGain(parent);
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

    //gets split result and add them to parent Node
    private void buildChildren(List<float[][]> splitResult, Node parent, int featureIndex, int numSamples) {
        if (!splitResult.isEmpty()) {
            for (int i = 0; i < splitResult.size(); i++) {
                Node childNode;
                if (splitResult.get(i) != null) {
                    float[] childValues = new float[numSamples];
                    for (int j = 0; j < splitResult.get(i).length; j++) {
                        childValues[j] = splitResult.get(i)[j][featureIndex];
                    }
                    if (checkLeaf(splitResult.get(i)))
                        childNode = new Node(childValues, true);
                    else
                        childNode = new Node(childValues, false);
                    parent.childrenNodes.add(childNode);
                }
            }
        }
    }

    //Removes an element from an ArrayList and return it at the end
    public void deleteFeature(ArrayList<Integer> featureArray, int featuretoRemove) {
        Iterator<Integer> iterator = featureArray.iterator();
        while (iterator.hasNext()) {
            int feature = iterator.next();
            if (feature == featuretoRemove) {
                iterator.remove();
            }
        }
    }

    //splits 2-D dataset array and pass a List of 2-D arrays of split data's
    public List<float[][]> split(float[][] dataset, float[] labels, int featureIndex, float[] thresholdValues, int numFeatures) {
        List<float[]> datasetList1 = new ArrayList<>();
        List<float[]> datasetList2 = new ArrayList<>();
        List<float[]> datasetlist3 = new ArrayList<>();
        List<float[]> datasetlist4 = new ArrayList<>();
        List<float[]> datasetlist5 = new ArrayList<>();
        List<float[]> datasetlist6 = new ArrayList<>();
        List<float[]> datasetlist7 = new ArrayList<>();

        for (int i = 0; i < dataset.length; i++) {
            if (dataset[i][featureIndex] <= thresholdValues[0]) {
                datasetList1.add(dataset[i]);
            } else if (dataset[i][featureIndex] > thresholdValues[0] && dataset[i][featureIndex] <= thresholdValues[1]) {
                datasetList2.add(dataset[i]);
            } else if (dataset[i][featureIndex] > thresholdValues[1] && dataset[i][featureIndex] <= thresholdValues[2]) {
                datasetlist3.add(dataset[i]);
            } else if (dataset[i][featureIndex] > thresholdValues[2] && dataset[i][featureIndex] <= thresholdValues[3]) {
                datasetlist4.add(dataset[i]);
            } else if (dataset[i][featureIndex] > thresholdValues[3] && dataset[i][featureIndex] <= thresholdValues[4]) {
                datasetlist5.add(dataset[i]);
            } else if (dataset[i][featureIndex] > thresholdValues[4] && dataset[i][featureIndex] <= thresholdValues[5]) {
                datasetlist6.add(dataset[i]);
            } else if (dataset[i][featureIndex] > thresholdValues[5] && dataset[i][featureIndex] <= thresholdValues[6]) {
                datasetlist7.add(dataset[i]);
            }
        }
        List<float[][]> datasetListresult = new ArrayList<>();
        pourList(datasetListresult, datasetList1, numFeatures);
        pourList(datasetListresult, datasetList2, numFeatures);
        pourList(datasetListresult, datasetlist3, numFeatures);
        pourList(datasetListresult, datasetlist4, numFeatures);
        pourList(datasetListresult, datasetlist5, numFeatures);
        pourList(datasetListresult, datasetlist6, numFeatures);
        pourList(datasetListresult, datasetlist7, numFeatures);
        return datasetListresult;
    }

    //Pours dataset-result List by getting each row of dataset List
    private void pourList(List<float[][]> datasetListresult, List<float[]> datasetlist, int numFeatures) {
        if (!datasetlist.isEmpty()) {
            float[][] temp = new float[datasetlist.size()][numFeatures];
            for (int j = 0; j < datasetlist.size(); j++) {
                for (int l = 0; l < numFeatures; l++) {
                    temp[j][l] = datasetlist.get(j)[l];
//                    System.out.print(" " + temp[j][l] + " ");
                }
//                System.out.println();
            }
            datasetListresult.add(temp);
        }
    }

    //find the element with the maximum occurrences and calculate the purity
    public float calculateLeafValue(float[][] featureTrain) {
        int lastIndex = featureTrain[0].length - 1; // Assuming the last column is at index 17

        // Count occurrences of each unique element in the last column
        Map<Float, Integer> countMap = new HashMap<>();
        for (int i = 0; i < featureTrain.length; i++) {
            float element = featureTrain[i][lastIndex];
            countMap.put(element, countMap.getOrDefault(element, 0) + 1);
            System.out.print("element " + element);
        }

        // Find the element with the maximum occurrences
        float mostRepeatedElement = 0;
        int maxOccurrences = 0;
        for (Map.Entry<Float, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                mostRepeatedElement = entry.getKey();
            }
        }

        // Calculate purity (occurrences of most repeated element / total elements)
        float purity = (float) maxOccurrences / featureTrain.length;
        System.out.println("Most Repeated Element: " + mostRepeatedElement);
        System.out.println("Purity: " + purity);
        return purity;
    }
    
    public float iGain(Node parentNode) {
        
        float[] weight = new float[parentNode.childrenNodes.size()];
        float sumEntropies = 0;
        for (int i = 0; i < parentNode.childrenNodes.size(); i++) {
            weight[i]= (float) parentNode.childrenNodes.size()/parentNode.value.length;
            sumEntropies += weight[i] * entropy(parentNode.childrenNodes.get(i).value);
        }
        if (sumEntropies == 0)
            System.out.println("this Node is leaf Node");
        float iGain = entropy(parentNode.value) - sumEntropies;
        parentNode.informationGain = iGain;
        return iGain;
    }


    public float entropy(float[] label) {
        Map<Float, Float> countMap = new HashMap<>();
        int sum = 0;
        for (float value : label) {
            countMap.put(value, countMap.getOrDefault(value, (float) 0) + 1);
            sum++;
        }
        float entropy = 0;
        for (float count : countMap.values()) {
            float probability = count / sum;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }
        return entropy;
    }
}