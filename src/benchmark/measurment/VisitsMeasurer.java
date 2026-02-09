package benchmark.measurment;

import oplossing.abstractTree.AbstractTree;
import oplossing.node.abstractNode.AbstractNode;

public class VisitsMeasurer<N extends AbstractNode<Integer, N>, T extends AbstractTree<Integer, N>>
    implements Measurer<T> {
    @Override
    public long before() {
        return 0;
    }

    @Override
    public long after(long before, T extra) {
        //        return extra.getVisits();
        return 0;
    }
}
