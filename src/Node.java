import java.util.ArrayList;
import java.util.List;

public class Node {
    boolean isLeaf;
    public int featureIndex;
    public List<Node> childrenNodes;
    public float informationGain;
    public float[] value;

    public Node(float[] value, boolean isLeaf) {
        this.childrenNodes = new ArrayList<>();
        this.value = value;
        this.isLeaf = isLeaf;
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
}