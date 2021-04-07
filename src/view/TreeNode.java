package view;

/**
 * Representation of a single tree node
 */
public class TreeNode {
    private final String value;
    private final TreeNode leftNode;
    private final TreeNode rightNode;

    public TreeNode(String value, TreeNode leftNode, TreeNode rightNode) {
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * Displays value of a tree node with a given prefix. Then it calls itself on the child nodes. It should be called
     * from the root.
     * @param prefix String that will be display with a tree node. To display a correct tree should be a empty string.
     * @param childrenPrefix String that will be display with a child nodes. To display a correct tree should be a empty string.
     */
    public void display(String prefix,String childrenPrefix){
        System.out.println(prefix + value);

        if(rightNode != null){
            rightNode.display(childrenPrefix + "├── ",childrenPrefix + "│   ");
        }

        if(leftNode != null){
            leftNode.display(childrenPrefix + "└── ",childrenPrefix + "    ");
        }
    }
}
