package Domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class TrieTree {
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

    public TrieTree(){
        root = new Node();
        root.element = 0;
    }

    public ByteArrayOutputStream comprimir(byte[] input) throws IOException {
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

    public static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
