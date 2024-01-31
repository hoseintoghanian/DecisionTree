import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    public int featureIndex;
    public float[] threshold;
    public List<Node> childrenNodes = null;
    public float infoGain;
    public float[] value;
    public List<float[]> childrenList = null;

    public Node(float[] value) {
        this.value = value;
        this.childrenNodes = new ArrayList<>();
        this.childrenList = new ArrayList<>();
        this.genChildren(this.childrenNodes);
        this.setValue(value);
    }

    public Node(float[] threshold, float infoGain, float[] value) {
        this.threshold = threshold;
        this.infoGain = infoGain;
        this.value = value;
        this.childrenNodes = new ArrayList<>();
        this.childrenList = new ArrayList<>();
        this.genChildren(this.childrenNodes);
        this.setValue(value);
    }

    public Node(float[] threshold, float infoGain, int featureIndex) {
        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.infoGain = infoGain;
    }

    public void setValue(float[] value) {
        System.arraycopy(value, 0, this.value, 0, value.length);
    }

    private void genChildren(List<Node> childrenNodes) {
        for (Node child : childrenNodes) {
            this.childrenList.add(child.getValue());
        }
    }

    public void addChild(Node child) {
        try {
            this.childrenNodes.add(child);
        } catch (RuntimeException exception) {
            throw new RuntimeException("couldn't add to childNodes List.");
        }
    }

    public float getInfoGain() {
        return infoGain;
    }

    public void setInfoGain(float infoGain) {
        this.infoGain = infoGain;
    }

    public float[] getValue() {
        return value;
    }

    public List<Node> getChildrenNodes() {
        return childrenNodes;
    }

    @Override
    public String toString() {
        return "Node{" +
                "childrenNodes=" + childrenNodes +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}
