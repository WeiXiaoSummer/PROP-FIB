package Domain;

public class NodePtr {
    public char value = 0x00;
    public boolean isLeaf = false;
    public boolean isRoot = false;
    public boolean isLeft = false;
    public NodePtr parent = null;
    public NodePtr lChildren = null;
    public NodePtr rChildren = null;

    public NodePtr() {}

    public NodePtr(char value) {
        this.value = value;
    }

    public void insertLeft(char value) {
        this.lChildren = new NodePtr(value);
        this.lChildren.parent = this;
        this.lChildren.isLeft = true;
    }

    public void insertRight(char value) {
        this.rChildren = new NodePtr(value);
        this.rChildren.parent = this;
        this.rChildren.isLeft = false;
    }

    //returns the node at the immediate right & at same level of this Node.

    public NodePtr getRightNode() {

        //node is the left child of it's parent, so we just returns the right child of the parent-node
        if (this.parent != null && this.isLeft) return this.parent.rChildren;

        //else node is the right child of it's parent, so we have to back to a specific previous nodes and then
        //get down to get the correspond right order node
        int count = 0; //this variable indicates how many levels we have backtracked and also how many nodes we need
        //to get down to get the correspond right order node, which is in the same level.
        NodePtr nptr = this;
        while (nptr.parent != null && !nptr.isLeft) {
            nptr = nptr.parent;
            ++count;
        }
        if (nptr.isRoot) return null;
        else nptr = nptr.parent.rChildren;
        while (count > 0) {
            nptr = nptr.lChildren;
            --count;
        }
        return nptr;
    }
}
