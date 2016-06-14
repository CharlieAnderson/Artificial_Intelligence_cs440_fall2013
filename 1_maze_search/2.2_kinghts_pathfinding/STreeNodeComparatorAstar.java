import java.util.Comparator;

public class STreeNodeComparatorAstar implements Comparator<STreeNode> {
    public int compare(STreeNode a, STreeNode b) {
        if (a.fn < b.fn) {
            return -1;
        }
        if (a.fn > b.fn) {
            return 1;
        }
        return 0;
    }
}
