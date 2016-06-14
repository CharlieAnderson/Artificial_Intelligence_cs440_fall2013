import java.util.Comparator;

public class STreeNodeComparatorUniformCost implements Comparator<STreeNode> {
    public int compare(STreeNode a, STreeNode b) {
        if (a.cost < b.cost) {
            return -1;
        }
        if (a.cost > b.cost) {
            return 1;
        }
        return 0;
    }
}
