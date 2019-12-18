package Domain;

import Commons.DomainLayerException;

public class HuffmanTree {

    private class NodePtr {
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
    }

    private NodePtr root;

    public HuffmanTree(char[] codesPerBitSize, char[] huffmanValues) {
        constructHuffmanTree(codesPerBitSize, huffmanValues);
    }

    public NodePtr getRoot() {
        return root;
    }

    private void constructHuffmanTree(char[] codesPerBitSize, char[] huffmanValues) {
        root = new NodePtr();
        root.setRoot(true);
        root.insertLeft((char)0x00);
        root.insertRight((char)0x00);
        NodePtr leftMostNode = root.getlChildren();

        int count = 0;
        for (int i = 1; i <= codesPerBitSize.length; ++i) {
            if (codesPerBitSize[i-1] == 0) {
                for (NodePtr nptr = leftMostNode; nptr != null; nptr = getRightNode(nptr)) {
                    nptr.insertLeft((char)0x00);
                    nptr.insertRight((char)0x00);
                }
                leftMostNode = leftMostNode.getlChildren();
            }

            else {
                for (int j = 0; j < codesPerBitSize[i-1]; ++j, ++count) {
                    leftMostNode.setValue(huffmanValues[count]);
                    leftMostNode.setLeaf(true);
                    leftMostNode = getRightNode(leftMostNode);
                }
                if (leftMostNode != null) {
                    leftMostNode.insertRight((char)0x00);
                    leftMostNode.insertLeft((char)0x00);
                    NodePtr nptr = getRightNode(leftMostNode);
                    leftMostNode = leftMostNode.getlChildren();

                    while (nptr != null) {
                        nptr.insertLeft((char)0x00);
                        nptr.insertRight((char)0x00);
                        nptr = getRightNode(nptr);
                    }
                }
            }
        }
    }

    //returns the node at the immediate right & at same level of this Node.
    public NodePtr getRightNode(NodePtr actualNode) {
        //node is the left child of it's parent, so we just returns the right child of the parent-node
        if (actualNode.getParent() != null && actualNode.isLeft()) return actualNode.getParent().getrChildren();

        //else node is the right child of it's parent, so we have to back to a specific previous nodes and then
        //get down to get the correspond right order node
        int count = 0; //this variable indicates how many levels we have backtracked and also how many nodes we need
        //to get down to get the correspond right order node, which is in the same level.
        NodePtr nptr = actualNode;
        while (nptr.getParent() != null && !nptr.isLeft()) {
            nptr = nptr.getParent();
            ++count;
        }
        if (nptr.equals(root)) return null;
        else nptr = nptr.getParent().getrChildren();
        while (count > 0) {
            nptr = nptr.getlChildren();
            --count;
        }
        return nptr;
    }

    public int decodeHuffmanCode(BitReader bitReader) throws DomainLayerException {
        NodePtr node = root;
        while (bitReader.next() && !node.isLeaf()) {
            boolean one = bitReader.readOne();
            if (one) node = node.getrChildren();
            else node = node.getlChildren();
        }
        return node.getValue();
    }


}
