package lab7;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

public class NPCTree extends JTree {
    DefaultMutableTreeNode npcsCollection = (DefaultMutableTreeNode) (super.getModel().getRoot());

    @Override
    public TreeModel getModel() {
        return super.getModel();
    }

    public NPCTree() {
        super(new DefaultMutableTreeNode("Collection"));
    }
    public NPCTree(String defaultNode) {
        super(new DefaultMutableTreeNode(defaultNode));
    }
}
