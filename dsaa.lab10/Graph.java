package dsaa.lab10;

import java.util.*;

public class Graph {
    int arr[][];
    HashMap<String, Integer> name2Int;
    @SuppressWarnings("unchecked")
    Map.Entry<String, Document>[] arrDoc;

    public Graph(SortedMap<String, Document> internet) {
        int size = internet.size();
        arr = new int[size][size];
        name2Int = new HashMap<>();
        arrDoc = new Map.Entry[size];

        int index = 0;
        for (Map.Entry<String, Document> entry : internet.entrySet()) {
            name2Int.put(entry.getKey(), index);
            arrDoc[index] = entry;
            index++;
        }

        // Initialize adjacency matrix
        for (int i = 0; i < size; i++) {
            Arrays.fill(arr[i], Integer.MAX_VALUE);
            arr[i][i] = 0;
        }

        // Fill adjacency matrix with link weights
        for (int i = 0; i < size; i++) {
            Document doc = arrDoc[i].getValue();
            for (Link l : doc.link) {
                Integer j = name2Int.get(l.ref);
                if (j != null) {
                    arr[i][j] = l.weight;
                }
            }
        }
    }



    public String dfs(String start) {
        Integer startIndex = name2Int.get(start);
        if (startIndex == null) return null;

        boolean[] visited = new boolean[arr.length];
        StringBuilder sb = new StringBuilder();
        dfsRecursive(startIndex, visited, sb);
        if (sb.length() >= 2) sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public String bfs(String start) {
        Integer startIndex = name2Int.get(start);
        if (startIndex == null) return null;

        boolean[] visited = new boolean[arr.length];
        Queue<Integer> queue = new LinkedList<>();
        TreeSet<String> toSort = new TreeSet<>();
        StringBuilder sb = new StringBuilder();

        queue.add(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            String currentName = arrDoc[current].getKey();
            sb.append(currentName).append(", ");

            toSort.clear();
            for (int i = 0; i < arr.length; i++) {
                if (arr[current][i] != Integer.MAX_VALUE && !visited[i] && i != current) {
                    toSort.add(arrDoc[i].getKey());
                }
            }

            for (String name : toSort) {
                int i = name2Int.get(name);
                visited[i] = true;
                queue.add(i);
            }
        }

        if (sb.length() >= 2) sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    private void dfsRecursive(int current, boolean[] visited, StringBuilder sb) {
        visited[current] = true;
        sb.append(arrDoc[current].getKey()).append(", ");

        TreeSet<String> toSort = new TreeSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[current][i] != Integer.MAX_VALUE && !visited[i] && i != current) {
                toSort.add(arrDoc[i].getKey());
            }
        }

        for (String name : toSort) {
            int i = name2Int.get(name);
            if (!visited[i]) {
                dfsRecursive(i, visited, sb);
            }
        }
    }

    public int connectedComponents() {
        DisjointSetForest dsf = new DisjointSetForest(arr.length);
        for (int i = 0; i < arr.length; i++) {
            dsf.makeSet(i);
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) { // tylko raz dla każdej pary (i < j)
                if (arr[i][j] != Integer.MAX_VALUE) {
                    dsf.union(i, j);
                }
            }
        }

        return dsf.countSets();
    }
}
