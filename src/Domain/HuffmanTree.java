package Domain;

import Commons.DomainLayerException;

/**
 * This class represents a Huffman tree used for decompression.
 */
public class HuffmanTree {

    /**
     * This inner class represents a Node pointer with stores information of huffman tree node.
     */
    private class NodePtr {
        /**
         * Value stored in this node.
         */
        private char value = 0x00;
        /**
         * Tells if this node is leaf.
         */
        private boolean Leaf = false;
        /**
         * Tells if this node is root.
         */
        private boolean Root = false;
        /**
         * Tells if this node is a left child.
         */
        private boolean Left = false;
        /**
         * Pointer that points to the father node of this node.
         */
        private NodePtr parent = null;
        /**
         * Pointer that points to the left children node of this node.
         */
        private NodePtr lChildren = null;
        /**
         * Pointer that points to the Right children node of this node.
         */
        private NodePtr rChildren = null;

        /**
         * Creates a new NodePtr.
         */
        public NodePtr() {}

        /**
         * Creates a new NodePtr with value = value.
         * @param value value to be set.
         */
        public NodePtr(char value) {
            this.value = value;
        }

        /**
         * Get the value of this NodePtr.
         * @return the value of this NodePtr.
         */
        public char getValue() { return this.value; }

        /**
         * Tells if this node is a leaf.
         * @return true if this node is a leaf, false otherwise.
         */
        public boolean isLeaf() { return this.Leaf; }

        /**
         * Tells if this node is a root.
         * @return true if this node is a root, false otherwise.
         */
        public boolean isRoot() { return this.Root; }

        /**
         * Tells if this node is a left child.
         * @return true if this node is a left child, false otherwise.
         */
        public boolean isLeft() { return this.Left; }

        /**
         * Return the Parent node of this node.
         * @return the parent node of this node.
         */
        public NodePtr getParent() { return this.parent; }

        /**
         * Return the left child node of this node.
         * @return the left child node.
         */
        public NodePtr getlChildren() { return this.lChildren; }

        /**
         * Return the right child node of this node.
         * @return the right children node.
         */
        public NodePtr getrChildren() { return this.rChildren; }

        /**
         * Sets the value of this node to value.
         * @param value the value to be set.
         */
        public void setValue(char value) { this.value = value; }

        /**
         * Sets the leaf attribute of this node to isLeaf.
         * @param isLeaf the value to be set.
         */
        public void setLeaf(boolean isLeaf) { this.Leaf = isLeaf; }

        /**
         * Sets the root attribute of this node to isRoot.
         * @param isRoot the value to be set.
         */
        public void setRoot(boolean isRoot) { this.Root = isRoot; }

        /**
         * Sets the isLeft attribute of this node to isLeft.
         * @param isLeft the value to be set.
         */
        public void setLeft(boolean isLeft) { this.Left = isLeft; }

        /**
         * Sets the parent of this node to parent.
         * @param parent the parent to be assigned.
         */
        public void setParent(NodePtr parent) { this.parent = parent; }

        /**
         * Sets the left children of this node to lChildren.
         * @param lChildren the left children to be assigned.
         */
        public void setlChildren(NodePtr lChildren) { this.lChildren = lChildren; }

        /**
         * Sets the right children of this node to rChildren.
         * @param rChildren the right children to be assigned.
         */
        public void setrChildren(NodePtr rChildren) { this.rChildren = rChildren; }

        /**
         * Inserts a left children with value = value to this node.
         * @param value the value of the left children to be assigned.
         */
        public void insertLeft(char value) {
            this.lChildren = new NodePtr(value);
            this.lChildren.parent = this;
            this.lChildren.Left = true;
        }

        /**
         * Inserts a right children with value = value to this node.
         * @param value the value of the right children to be assigned.
         */
        public void insertRight(char value) {
            this.rChildren = new NodePtr(value);
            this.rChildren.parent = this;
            this.rChildren.Left = false;
        }
    }

    /**
     * The root node of this huffman tree.
     */
    private NodePtr root;

    /**
     * Creates a new huffman tree and initialize it with the given information.
     * @param codesPerBitSize codes lengths and it's total numbers.
     * @param huffmanValues huffman codes.
     */
    public HuffmanTree(char[] codesPerBitSize, char[] huffmanValues) {
        constructHuffmanTree(codesPerBitSize, huffmanValues);
    }

    /**
     * Get the root node of this huffman tree.
     * @return the root node
     */
    public NodePtr getRoot() {
        return root;
    }

    /**
     * Initializes this huffman tree with the given information.
     * @param codesPerBitSize codes length and it's total numbers.
     * @param huffmanValues huffman codes.
     */
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

    /**
     * Returns the node at the immediate right and at same level of a given node.
     * @param actualNode the node that we wishes to find it's corresponding right node.
     * @return the right node of the actualNode.
     */
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

    /**
     * Decodes the huffman code read from the bitReader.
     * @param bitReader bitReader from which the huffman code is read.
     * @return the corresponding decoded information
     * @throws DomainLayerException is a error occurs during the decode process.
     */
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
