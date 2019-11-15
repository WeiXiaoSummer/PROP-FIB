package Domain;

public class HuffmanTree {
    private NodePtr root;

    public HuffmanTree(char[] codesPerBitSize, char[] huffmanValues) {
        constructHuffmanTree(codesPerBitSize, huffmanValues);
    }

    private void constructHuffmanTree(char[] codesPerBitSize, char[] huffmanValues) {
        root = new NodePtr();
        root.isRoot = true;
        root.insertLeft((char)0x00);
        root.insertRight((char)0x00);
        NodePtr leftMostNode = root.lChildren;

        int count = 0;
        for (int i = 1; i <= codesPerBitSize.length; ++i) {
            if (codesPerBitSize[i-1] == 0) {
                for (NodePtr nptr = leftMostNode; nptr != null; nptr = nptr.getRightNode()) {
                    nptr.insertLeft((char)0x00);
                    nptr.insertRight((char)0x00);
                }
                leftMostNode = leftMostNode.lChildren;
            }

            else {
                for (int j = 0; j < codesPerBitSize[i-1]; ++j, ++count) {
                    leftMostNode.value = huffmanValues[count];
                    leftMostNode.isLeaf = true;
                    leftMostNode = leftMostNode.getRightNode();
                }
                if (leftMostNode != null) {
                    leftMostNode.insertRight((char)0x00);
                    leftMostNode.insertLeft((char)0x00);
                    NodePtr nptr = leftMostNode.getRightNode();
                    leftMostNode = leftMostNode.lChildren;

                    while (nptr != null) {
                        nptr.insertLeft((char)0x00);
                        nptr.insertRight((char)0x00);
                        nptr = nptr.getRightNode();
                    }
                }
            }
        }
    }

    public int decodeHuffmanCode(BitReader bitReader) {
        NodePtr node = root;
        while (bitReader.next() && !node.isLeaf) {
            boolean one = bitReader.readOne();
            if (one) node = node.rChildren;
            else node = node.lChildren;
        }
        return node.value;
    }
}
