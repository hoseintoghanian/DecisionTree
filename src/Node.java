import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    public int featureIndex;
    public double[] threshold;
    public List<Node> childrenNodes = null;
    public double infoGain;

    public double[] value;
    public List<double[]> childrenDoubleList = null;

    public Node(double[] value) {
        this.value = value;
        this.childrenNodes = new ArrayList<>();
        this.childrenDoubleList = new ArrayList<>();
        this.genChildren(this.childrenNodes);
        this.setValue(value);
    }

    public Node(double[] threshold, double infoGain, double[] value) {
        this.threshold = threshold;
        this.infoGain = infoGain;
        this.value = value;
        this.childrenNodes = new ArrayList<>();
        this.childrenDoubleList = new ArrayList<>();
        this.genChildren(this.childrenNodes);
        this.setValue(value);
    }

    public Node(double[] threshold, double infoGain, int featureIndex) {
        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.infoGain = infoGain;
    }

    public void setValue(double[] value) {
        System.arraycopy(value, 0, this.value, 0, value.length);
    }

    private void genChildren(List<Node> childrenNodes) {
        for (Node child : childrenNodes) {
            this.childrenDoubleList.add(child.getValue());
        }
    }

    public void addChild(Node child) {
        //try {
            this.childrenNodes.add(child);
        //} catch (RuntimeException exception) {
            //throw new RuntimeException("couldn't add to childNodes List.");
        //}
    }

    public double getInfoGain() {
        return infoGain;
    }

    public void setInfoGain(double infoGain) {
        this.infoGain = infoGain;
    }

    public double[] getValue() {
        return value;
    }

    public List<Node> getChildrenNodes() {
        return childrenNodes;
    }

    /*public void setChildrenNodes(List<Node> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    public List<double[]> getChildrenDoubleList() {
        return childrenDoubleList;
    }

    public void setChildrenDoubleList(List<double[]> childrenDoubleList) {
        this.childrenDoubleList = childrenDoubleList;
    }*/

    @Override
    public String toString() {
        return "Node{" +
                "childrenNodes=" + childrenNodes +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}
