
import java.io.FileOutputStream;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.lang.String;


class HuffmanNode {
    int data; // encoded value
    char c; // key value

    HuffmanNode left; // left subtree
    HuffmanNode right; // right subtree
} // end class HuffmanNode

class MyComparator implements Comparator<HuffmanNode> {
    // compare two nodes
    public int compare (HuffmanNode x, HuffmanNode y){
        // return sum for evaluation
        // test push
        return x.data - y.data;
    }
} // end class MyComparator

public class Huffman {
    // recursive function prints huffman-code via tree traversal
    // s is generated huffman code

    public static void CalcFreq(String str, HashMap<Character, Integer> map) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            map.merge(c, 1, Integer::sum);
        }
    }

    public static void PrintCode(HuffmanNode node, String s, HashMap<Character, String> codeMap){
        int ascii = node.c;
        if (node.left == null && node.right == null &&
                ascii >= 0 && ascii <= 255) {
            //System.out.println(s);
            codeMap.putIfAbsent(node.c, s);
            return;
        }
        //System.out.print(root.left.c);
        PrintCode(node.left, s + "0", codeMap);
        PrintCode(node.right, s + "1", codeMap);
    } // end class Huffman

    public static String HuffDecode(HuffmanNode root, String s) {
        String answer = "";
        HuffmanNode current = root;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0'){
                current = current.left;
            }
            else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                answer += current.c;
                current = root;
            }
        }
        String s1 = answer + "\n\0";
        return s1;
    }

    public static void main(String[] args){
        /* Scanner s = new Scanner(System.in); */

        HashMap<Character, Integer> freq = new HashMap<Character,Integer>(); // stores character frequency in string
        HashMap<Character, String> codeMap = new HashMap<Character,String>();
        String test = "This is a longer test string with some characters !?,''''(*&^";
        List <Character> keyList = new ArrayList<Character>();
        List <Integer> valList = new ArrayList<Integer>();
        String encodedString = new String();

        CalcFreq(test, freq);

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            Character ch = entry.getKey();
            Integer in = entry.getValue();
            keyList.add(ch);
            valList.add(in);
        }

        //create priority queue
        PriorityQueue<HuffmanNode> q =
                new PriorityQueue<HuffmanNode>(freq.size(), new MyComparator());

        for (int i = 0; i < freq.size(); i++) {
            HuffmanNode hn = new HuffmanNode();

            hn.c = keyList.get(i);
            hn.data = valList.get(i);
            //System.out.print(hn.c);

            hn.left = null;
            hn.right = null;

            q.add(hn);
            //System.out.print(q);
        }

        HuffmanNode root = null;

        while (q.size() > 1) {

            // extract first min
            HuffmanNode x = q.peek();
            q.poll();

            // extract second min
            HuffmanNode y = q.peek();
            q.poll();

            HuffmanNode f = new HuffmanNode();

            // add some frequency of 2 nodes
            f.data = x.data + y.data;
            //System.out.print(f.data);
            f.c = '#';
            // first extraction to left subtree
            f.left = x;
            // second extraction to right subtree
            f.right = y;
            // f is now root
            root = f;
            // add to this priority queue
            q.add(f);
        }
        PrintCode(root, "", codeMap);

        String code = new String();
        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            code += (String)codeMap.get(c);
        }
        System.out.print(code + '\n');
        System.out.print(HuffDecode(root, code));

    }
}