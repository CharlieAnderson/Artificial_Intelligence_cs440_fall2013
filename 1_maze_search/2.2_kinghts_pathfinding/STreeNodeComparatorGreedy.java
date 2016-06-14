import java.util.Comparator;

public class STreeNodeComparatorGreedy implements Comparator<STreeNode> {
    public int compare(STreeNode a, STreeNode b) {
        if (a.hn < b.hn) {
            return -1;
        }
        if (a.hn > b.hn) {
            return 1;
        }
        return 0;
    }
}
