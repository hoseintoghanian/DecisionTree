import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
		// Load your dataset
		String[] colNames = {"HighBP", "HighChol", "CholCheck", "Smoker", "Stroke", "HeartDiseaseorAttack",
				"PhysActivity", "Fruits", "Veggies", "HvyAlcoholConsump", "AnyHealthcare",
				"NoDocbcCost", "GenHlth", "DiffWalk", "Sex", "Education", "Income"};

		float[][] feature = readCSV("Data/feature_train.csv", colNames);
		float[][] label = readCSV("Data/label_train.csv" , colNames);

		float [] label_temp = new float[label.length];
		for (int i = 0; i < label.length; i++) {
			label_temp[i] = label[i][0];
		}

		// Run ID3
		Tree tree = new Tree(0 , 0);
//		DecisionTreeClassifier Dtree = new DecisionTreeClassifier(tree , feature , label_temp);
//		Node root = Dtree.buildTree(feature, 0 , 8878 , 5);
		System.out.println("Generated decision tree:");

//		// Train-Test split
//		double[][] X = new double[feature.length][feature[0].length - 1];
//		double[] Y = new double[feature.length];
//		for (int i = 0; i < feature.length; i++) {
//			System.arraycopy(feature[i], 0, X[i], 0, feature[i].length - 1);
//			Y[i] = feature[i][feature[i].length - 1];
//		}
//
//		int randomState = 41;
//		double[][] X_train, X_test;
//		double[] Y_train, Y_test;
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
//		double[] Y_pred = classifier.predict(X_test);
//		System.out.println(Arrays.toString(Y_pred));
//
//		// Assuming accuracy_score is similar to Python's sklearn.metrics.accuracy_score
//		double accuracy = accuracyScore(Y_test, Y_pred);
//		System.out.println("Accuracy: " + accuracy);

        // Example usage with a list of children Nodes
        Node child1 = new Node(new float[]{1.0F, 1.0F, 0.0F , 2.0F , 3.0F , 3.0F});
        Node child2 = new Node(new float[]{1.0F, 1.0F, 1.0F , 5.0F});
        Node Parent = new Node(new float[]{0} , 0 , new float[]{1.0F, 0.0F, 1.0F, 2.0F, 3.0F});
        Parent.addChild(child1);
        Parent.addChild(child2);
		Tree Test_tree = new Tree(Parent);
        Test_tree.informationGain(Parent);
        System.out.println("information gain of " + Arrays.toString(Parent.getValue()) + " Node is : " + Parent.getInfoGain());
        Test_tree.informationGain(child1);
        System.out.println("information gain of " + Arrays.toString(child1.getValue()) + " Node is : " + child1.getInfoGain());
	}

	//reads csv file line by line and passes an array of its data's
	public static float[][] readCSV(String filePath, String[] colNames) {
		List<float[]> dataList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			br.readLine(); // skip header
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				float[] row = new float[values.length];
				for (int i = 0; i < values.length; i++) {
					row[i] = Float.parseFloat(values[i]);
				}
				dataList.add(row);
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