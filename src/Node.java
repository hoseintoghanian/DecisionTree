import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    private int featureIndex;
    private float[] threshold;
    private List<Node> childrenNodes;
    private float infoGain;
    private float[] value;
    private float[][] value2;
    private List<float[]> childrenList = null;
    Boolean isLeaf;


    public Node(float[] value) {
        this.value = value;
        this.childrenNodes = new ArrayList<>();
        this.childrenList = new ArrayList<>();
        this.genChildren(this.childrenNodes);
        this.setValue(value);
    }

    public Node(float[] value, Boolean isLeaf) {
        this.childrenNodes = new ArrayList<>();
        this.value = value;
        this.isLeaf = isLeaf;
    }

    public void setValue ( float[] value){
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

    public void setChildrenNodes(List<Node> childrenNodes) {
        this.childrenNodes = childrenNodes;
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

    public List<float[]> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<float[]> childrenList) {
        this.childrenList = childrenList;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    @Override
    public String toString() {
        return "Node{" +
                "childrenNodes=" + childrenNodes +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}