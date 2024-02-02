import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int numberOfLabels = 0;

    public static void main(String[] args) {
        String[] featureNames = {"HighBP", "HighChol", "CholCheck", "Smoker", "Stroke",
                "HeartDiseaseorAttack", "PhysActivity", "Fruits", "Veggies",
                "HvyAlcoholConsump", "AnyHealthcare", "NoDocbcCost", "GenHlth",
                "DiffWalk", "Sex", "Education", "Income"};

        float[][] feature = readCSV("Data/feature_train.csv");
        float[][] label = readCSV("Data/label_train.csv");

        numberOfLabels /= 2;

        float[][] featureCopy = new float[15][17];
        for (int i = 0; i < 15; i++) {
            System.arraycopy(feature[i], 0, featureCopy[i], 0, 17);
        }

        float[] labelCopy = new float[numberOfLabels];
        for (int i = 0; i < numberOfLabels; i++) {
            labelCopy[i] = label[i][0];
        }

        Tree tree = new Tree(0, 0);

        ArrayList<Integer> featureArr = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            featureArr.add(i, i);
        }
        DecisionTreeClassifier decisionTree = new DecisionTreeClassifier(tree, featureCopy, labelCopy);
        Node root = decisionTree.buildTree(featureCopy, 0, 6, featureArr);
        System.out.println("Decision tree Generated");

//		// Train-Test split
//		float[][] X = new float[feature.length][feature[0].length - 1];
//		float[] Y = new float[feature.length];
//		for (int i = 0; i < feature.length; i++) {
//			System.arraycopy(feature[i], 0, X[i], 0, feature[i].length - 1);
//			Y[i] = feature[i][feature[i].length - 1];
//		}
//
//		int randomState = 41;
//		float[][] X_train, X_test;
//		float[] Y_train, Y_test;
//
//		// Assuming train_test_split is similar to Python's sklearn.model_selection.train_test_split
//		trainTestSplit(X, Y, 0.2, randomState, X_train, X_test, Y_train, Y_test);
//
//		// Fit the model
//		DecisionTreeClassifier classifier = new DecisionTreeClassifier(3, 3);
//		classifier.fit(X_train, Y_train);
//		classifier.printTree();
//
//		// Test the model
//		float[] Y_pred = classifier.predict(X_test);
//		System.out.println(Arrays.toString(Y_pred));
//
//		// Assuming accuracy_score is similar to Python's sklearn.metrics.accuracy_score
//		float accuracy = accuracyScore(Y_test, Y_pred);
//		System.out.println("Accuracy: " + accuracy);

        // Example usage with a list of children Nodes
//        Node child1 = new Node(new float[]{1.0, 1.0, 0.0 , 2.0 , 3.0 , 3.0});
//        Node child2 = new Node(new float[]{1.0, 1.0, 1.0 , 5.0});
//        Node Parent = new Node(new float[]{0} , 0 , new float[]{1.0, 0.0, 1.0, 2.0, 3.0});
//        Parent.addChild(child1);
//        Parent.addChild(child2);
//		Tree Test_tree = new Tree(Parent);
//        Test_tree.informationGain(Parent);
//        System.out.println("information gain of " + Arrays.toString(Parent.getValue()) + " Node is : " + Parent.getInfoGain());
//        Test_tree.informationGain(child1);
//        System.out.println("information gain of " + Arrays.toString(child1.getValue()) + " Node is : " + child1.getInfoGain());
    }

    public static float[][] readCSV(String fileAddress) {
        List<float[]> dataList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileAddress))) {
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                float[] row = new float[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Float.parseFloat(values[i]);
                }
                dataList.add(row);
                numberOfLabels++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        float[][] data = new float[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        return data;
    }
}