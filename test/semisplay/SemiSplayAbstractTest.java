package semisplay;

import abstractTests.SemiSplayTest;
import oplossing.SemiSplayTree;

public abstract class SemiSplayAbstractTest extends SemiSplayTest<SemiSplayTree<Integer>> {
    @Override
    protected SemiSplayTree<Integer> createTree() {
        return new SemiSplayTree<>();
    }
}
