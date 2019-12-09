package Domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class TrieTree {

        class Node{
            public int element;//单词的当前字
            boolean wordEnd;//单词结束标志
            HashMap<Byte, Node> childdren = null;

            public Node(){
                childdren = new HashMap<Byte, Node>();
            }

            public Node(int item){
                this.element = item;
                childdren = new HashMap<Byte, Node>();
            }
        }

        public Node root;
        public int key = 0;

        //构造函数
        public TrieTree(){
            root = new Node();
            root.element = 0;
        }

        public ByteArrayOutputStream comprimir(byte[] input) throws IOException {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Node currentNode = root;
            int i = 0;
            while (i < input.length) {
                Node childNode = currentNode.childdren.get(input[i]);
                if (childNode == null) {
                    Node child = new Node(++key);
                    currentNode.childdren.put(input[i], child);
                    output.write(intToByteArray(currentNode.element));
                    if (i < input.length)output.write(input[i]);
                    currentNode = root;
                } else {
                    currentNode = childNode;
                    if (i+1 == input.length)output.write(intToByteArray(currentNode.element));
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
