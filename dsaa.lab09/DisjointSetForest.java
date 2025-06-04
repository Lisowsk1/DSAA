package dsaa.lab09;

public class DisjointSetForest implements DisjointSetDataStructure {

    private class Element {
        int rank;
        int parent;
    }

    Element[] arr;

    public DisjointSetForest(int size) {
        arr = new Element[size];
    }

    @Override
    public void makeSet(int item) {
        arr[item] = new Element();
        arr[item].parent = item;
        arr[item].rank = 0;
    }

    @Override
    public int findSet(int item) {
        if (arr[item].parent == item) return item;
        else {
            int head = findSet(arr[item].parent);
            arr[item].parent = head;
            return head;
        }
    }

    @Override
    public boolean union(int itemA, int itemB) {
        int repA = findSet(itemA);
        int repB = findSet(itemB);

        if (repA == repB)
            return false;

        if (arr[repA].rank > arr[repB].rank) {
            link(repA, repB);
        } else {
            link(repB, repA);
        }
        return true;
    }

    private void link(int upper, int lower) {
        arr[lower].parent = upper;
        if (arr[lower].rank == arr[upper].rank)
            arr[upper].rank++;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Disjoint sets as forest:\n");

        for (int i = 0; i < arr.length; i++) {
            sb.append(i).append(" -> ").append(arr[i].parent).append("\n");
        }

        return sb.toString().stripTrailing();
    }
}
