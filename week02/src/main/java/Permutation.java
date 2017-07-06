import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int inputLength = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        for (int i = 0; i < inputLength; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
