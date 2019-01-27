import java.util.Stack;

public class TreeSearch {
    /**
     * A tree class for implementing search against.  This uses null
     * instead of Optional for simplicity and backwards compatibility.
     *
     * NOTE: This is *not* a search tree.
     */
    private static class Tree<T> {
        public Tree(T data) {
            this(data, null, null);
        }

        public Tree(T data, Tree<T> left, Tree<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public final T data;
        public final Tree<T> left;
        public final Tree<T> right;

        /**
         * Search for "needle" inside of this tree.
         *
         * @returns the node that contains "needle" or null.
         */
        public Tree search(T needle) {
            return this;
        }

        // Helper methods to make testing/debugging easier.
        public static <T> Tree<T> fromArray(T[] data) {
            final Stack<Tree<T>> forest = new Stack<>();

            for (int i = 0; i < data.length; i++) {
                if (i <= data.length / 2) {
                    // The first half is the leaves.
                    forest.push(new Tree<T>(data[i]));
                } else {
                    final Tree<T> left = forest.pop();
                    final Tree<T> right = forest.empty() ? null : forest.pop();
                    forest.push(new Tree<T>(data[i], left, right));
                }
            }

            return forest.pop();
        }

        public String toString() {
            String output = this.data.toString();

            if (this.left == null && this.right == null) {
                return output;
            }

            output += "{";

            if (this.left != null) {
                output += this.left;
            }

            if (this.left != null && this.right != null) {
                output += ":";
            }

            if (this.right != null) {
                output += this.right;
            }


            return output + "}";
        }

    }

    private static void expect(String name, Object actual, Object expected) {
        if (actual == expected) {
            System.out.println("pass: " + name);
        } else {
            System.out.println("FAIL: " + name);
        }
    }

    public static void main(String[] args) {
        Tree<Integer> rootTree = Tree.fromArray(new Integer[] {1});
        expect("root tree, exists", rootTree.search(1), rootTree);
        expect("root tree, not exists", rootTree.search(2), null);

        Tree<String> deepTree = Tree.fromArray(
            new String[] {"right child",
                          "right leaf",
                          "left leaf",
                          "left child",
                          "root"});
        expect("deep tree, root", deepTree.search("root"), deepTree);
        expect("deep tree, left",
               deepTree.search("left child"),
               deepTree.left);
        expect("deep tree, left, right",
               deepTree.search("right leaf"),
               deepTree.left.right);
    }
}
