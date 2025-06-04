package dsaa.lab09;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

    private class Element {
        int representant;
        int next;
        int length;
        int last;
    }

    private static final int NULL = -1;

    Element arr[];

    public DisjointSetLinkedList(int size) {
        arr = new Element[size];
    }

    @Override
    public void makeSet(int item) {
        arr[item] = new Element();
        arr[item].representant = item;
        arr[item].next = NULL;
        arr[item].length = 1;
        arr[item].last = item;
    }

    @Override
    public int findSet(int item) {
        return arr[item].representant;
    }

    @Override
    public boolean union(int itemA, int itemB) {
        int repA = findSet(itemA);
        int repB = findSet(itemB);

        if (repA == repB)
            return false;

        // always attach shorter to longer
        if (arr[repA].length >= arr[repB].length) {
            append(repA, repB);
        } else {
            append(repB, repA);
        }

        return true;
    }

    private void append(int longerHead, int shorterHead) {
        // Update tail of longer to point to head of shorter
        arr[arr[longerHead].last].next = shorterHead;

        // Traverse the shorter list and update each node's representant
        int current = shorterHead;
        while (current != NULL) {
            arr[current].representant = longerHead;
            current = arr[current].next;
        }

        // Update the longer list
        arr[longerHead].last = arr[shorterHead].last;
        arr[longerHead].length += arr[shorterHead].length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Disjoint sets as linked list:\n");

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].representant == i) {
                StringBuilder line = new StringBuilder();
                int current = i;
                while (current != NULL) {
                    if (line.length() > 0)
                        line.append(", ");
                    line.append(current);
                    current = arr[current].next;
                }
                sb.append(line).append("\n");
            }
        }

        return sb.toString().stripTrailing();
    }
}
