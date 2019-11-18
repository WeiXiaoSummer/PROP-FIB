package Domain;

public class NodePtr {
    private char value = 0x00;
    private boolean Leaf = false;
    private boolean Root = false;
    private boolean Left = false;
    private NodePtr parent = null;
    private NodePtr lChildren = null;
    private NodePtr rChildren = null;

    public NodePtr() {}

    public NodePtr(char value) {
        this.value = value;
    }

    public char getValue() { return this.value; }

    public boolean isLeaf() { return this.Leaf; }

    public boolean isRoot() { return this.Root; }

    public boolean isLeft() { return this.Left; }

    public NodePtr getParent() { return this.parent; }

    public NodePtr getlChildren() { return this.lChildren; }

    public NodePtr getrChildren() { return this.rChildren; }

    public void setValue(char value) { this.value = value; }

    public void setLeaf(boolean isLeaf) { this.Leaf = isLeaf; }

    public void setRoot(boolean isRoot) { this.Root = isRoot; }

    public void setLeft(boolean isLeft) { this.Left = isLeft; }

    public void setParent(NodePtr parent) { this.parent = parent; }

    public void setlChildren(NodePtr lChildren) { this.lChildren = lChildren; }

    public void setrChildren(NodePtr rChildren) { this.rChildren = rChildren; }

    public void insertLeft(char value) {
        this.lChildren = new NodePtr(value);
        this.lChildren.parent = this;
        this.lChildren.Left = true;
    }

    public void insertRight(char value) {
        this.rChildren = new NodePtr(value);
        this.rChildren.parent = this;
        this.rChildren.Left = false;
    }

    //returns the node at the immediate right & at same level of this Node.
}
