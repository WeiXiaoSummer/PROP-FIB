package Drivers.JPEG;

public class HuffmanTree {
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

    public int decodeHuffmanCode(BitReader bitReader) {
        NodePtr node = root;
        while (bitReader.next() && !node.isLeaf()) {
            boolean one = bitReader.readOne();
            if (one) node = node.getrChildren();
            else node = node.getlChildren();
        }
        return node.getValue();
    }
}
