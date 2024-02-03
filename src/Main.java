import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        String[] featureNames = {"HighBP", "HighChol", "CholCheck", "Smoker", "Stroke",
//                "HeartDiseaseorAttack", "PhysActivity", "Fruits", "Veggies",
//                "HvyAlcoholConsump", "AnyHealthcare", "NoDocbcCost", "GenHlth",
//                "DiffWalk", "Sex", "Education", "Income"};

        float[][] firstFeatureTrain = readDataFiles("Data/feature_train.csv");
        float[][] firstLabelTrain = readDataFiles("Data/label_train.csv");
        float[][] firstFeatureTest = readDataFiles("Data/feature_test.csv");
        float[][] firstLabelTest = readDataFiles("Data/label_test.csv");

        float[] labelTrain = new float[firstLabelTrain.length];
        for (int i = 0; i < firstLabelTrain.length; i++) {
            labelTrain[i] = firstLabelTrain[i][0];
        }

        float[] labelTest = new float[firstLabelTest.length];
        for (int i = 0; i < firstLabelTest.length; i++) {
            labelTest[i] = firstLabelTest[i][0];
        }

        Tree tree = new Tree(0);

        // Small temporary test on dataset
        float[][] data = new float[30][18];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 17; j++) {
                data[i][j] = firstFeatureTrain[i][j];
//                    System.out.print(data[i][j] + " ");
            }
//                System.out.println();
        }
        System.out.println("Dataset test = ");
        float[][] temp_test = new float[30][18];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 17; j++) {
                temp_test[i][j] = firstFeatureTest[i][j];
                System.out.print(temp_test[i][j] + " ");
            }
            System.out.println();
        }


        ArrayList<Integer> featureArr = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            featureArr.add(i, i);
        }
        for (int i = 0; i < data.length; i++) {
            data[i][data[0].length - 1] = labelTrain[i];
        }

        // Train-Test split
        DTreeClassifier dTreeClassifier = new DTreeClassifier(data, labelTrain);
        Node root = tree.createTree(data, 0, featureArr);
        System.out.println("Decision tree Built Successfully  * o *");

        // Test the model
        System.out.print("predicted Labels : ");
        float[] predictedLabels = dTreeClassifier.makePrediction(temp_test, new Tree(root), featureArr);
        for (int i = 0; i < predictedLabels.length; i++) {
            System.out.print(predictedLabels[i] + " ");
        }
        int[] label_test_temporary = new int[predictedLabels.length];
        for (int i = 0; i < predictedLabels.length; i++) {
            label_test_temporary[i] = (int) labelTest[i];
        }
        // Assuming accuracy_score
        float accuracy = dTreeClassifier.accuracy(label_test_temporary, predictedLabels);

        if (accuracy < 0.75) {
            System.out.println("Your Fucking accuracy is = " + accuracy);
        } else {
            System.out.println(" ***  Congratulations *o*  ***");
            float myfloat = (float) (accuracy / 100.0);
            System.out.printf("Your Algorithm has %.2f accuracy !!!%n \n", myfloat);
        }
    }

    public static float[][] readDataFiles(String fileAddress) {
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