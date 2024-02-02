import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    public int featureIndex;
    public float[] threshold;
    public List<Node> childrenNodes;
    public float infoGain;
    public float[] value;
    public float[][] value2;
    public List<float[]> childrenList = null;
    boolean isLeaf;

    public Node(float[] value) {
        this.value = value;
        this.childrenNodes = new ArrayList<>();
        this.childrenList = new ArrayList<>();
        this.generateChildren(this.childrenNodes);
        this.setValue(value);
    }

    public Node(float[] value, boolean isLeaf) {
        this.childrenNodes = new ArrayList<>();
        this.value = value;
        this.isLeaf = isLeaf;
    }

    private void generateChildren(List<Node> childrenNodes) {
        for (Node child : childrenNodes) {
            this.childrenList.add(child.getValue());
        }
    }

    public void addChild(Node child) {
        this.childrenNodes.add(child);
    }

    public void setInfoGain(float infoGain) {
        this.infoGain = infoGain;
    }

    public float[] getValue() {
        return value;
    }

    public void setValue(float[] value) {
        System.arraycopy(value, 0, this.value, 0, value.length);
    }

    public List<Node> getChildrenNodes() {
        return childrenNodes;
    }

    public Node getChildrenByIndex(int index) {
        int i = 0;
        for (Node child : this.childrenNodes) {
            if (i == index)
                return child;
            i++;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Node{childrenNodes= " + childrenNodes +
                " , value= " + Arrays.toString(value) + " }";
    }
}