package Domain;

import Commons.DomainLayerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * trie Tree use for compress
 */
public class TrieTree {
    /**
     * construct a node of trieTree
     */
    class Node{
        public int element;
        HashMap<Byte, Node> childdren = null;

        public Node(){
            childdren = new HashMap<Byte, Node>();
        }

        public Node(int item){
            this.element = item;
            childdren = new HashMap<Byte, Node>();
        }
    }

    private Node root;
    private int key = 0;

    /**
     * construct a trieTree
     */
    public TrieTree(){
        root = new Node();
        root.element = 0;
    }

    /**
     * compress input with trieTree, write the result into a ByteArrayOutputStream and return it
     * @param input byte array need to compress
     * @return output compressed
     * @throws DomainLayerException
     */
    public ByteArrayOutputStream comprimir(byte[] input) throws DomainLayerException {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            // start at root of tree
            Node currentNode = root;
            int i = 0;
            while (i < input.length) {
                // check if currentNode has a child with char -> input[i]
                Node childNode = currentNode.childdren.get(input[i]);
                // if don't has
                if (childNode == null) {
                    //create a child with char -> input[i]
                    Node child = new Node(++key);
                    currentNode.childdren.put(input[i], child);
                    output.write(intToByteArray(currentNode.element));
                    if (i < input.length)output.write(input[i]);
                    currentNode = root;
                }
                // if has, continue check the childnode, until can't get the repeating byte
                else {
                    currentNode = childNode;
                    // output the number of nodes we have check and it isn't null.
                    if (i+1 == input.length) output.write(intToByteArray(currentNode.element));
                }
                ++i;
            }
            return output;
        }
        catch (IOException e) { throw  new DomainLayerException(" "); }
    }

    /**
     * transfer int to byte array and return byte array
     * @param value which int we want to transfer
     * @return byte array is the result of transfer
     */
    private static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
