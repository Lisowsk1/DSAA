package dsaa.lab11;

import java.util.*;
import java.util.Map.Entry;

public class Graph {
	int[][] arr;
	HashMap<String, Integer> name2Int;
	@SuppressWarnings("unchecked")
	Map.Entry<String, Document>[] arrDoc;

	public Graph(SortedMap<String, Document> internet) {
		int size = internet.size();
		arr = new int[size][size];
		name2Int = new HashMap<>();
		arrDoc = new Map.Entry[size];

		int idx = 0;
		for (Map.Entry<String, Document> entry : internet.entrySet()) {
			arrDoc[idx] = entry;
			name2Int.put(entry.getKey(), idx);
			idx++;
		}

		for (int i = 0; i < size; i++) {
			Document doc = arrDoc[i].getValue();
			for (Link lnk : doc.link) {
				String target = lnk.getRef();
				Integer j = name2Int.get(target);
				if (j != null) {
					arr[i][j] = lnk.getWeight();
				}

			}
		}
	}

	public String bfs(String start) {
		if (start == null) return null;
		Integer startIndex = name2Int.get(start.toLowerCase());
		if (startIndex == null) {
			return null;
		}

		boolean[] visited = new boolean[arr.length];
		Queue<Integer> queue = new LinkedList<>();
		TreeSet<String> toSort = new TreeSet<>();
		StringBuilder sb = new StringBuilder();

		visited[startIndex] = true;
		queue.add(startIndex);

		while (!queue.isEmpty()) {
			int current = queue.poll();
			String currentName = arrDoc[current].getKey();
			sb.append(currentName).append(", ");

			toSort.clear();
			for (int i = 0; i < arr.length; i++) {
				if (arr[current][i] > 0 && !visited[i]) {
					toSort.add(arrDoc[i].getKey());
				}
			}

			for (String name : toSort) {
				int i = name2Int.get(name);
				if (!visited[i]) {
					visited[i] = true;
					queue.add(i);
				}
			}
		}

		if (sb.length() >= 2) {
			sb.setLength(sb.length() - 2);
		}
		return sb.toString();
	}

	public String dfs(String start) {
		if (start == null) return null;
		Integer startIndex = name2Int.get(start.toLowerCase());
		if (startIndex == null) {
			return null;
		}

		boolean[] visited = new boolean[arr.length];
		StringBuilder sb = new StringBuilder();
		dfsRecursive(startIndex, visited, sb);

		if (sb.length() >= 2) {
			sb.setLength(sb.length() - 2);
		}
		return sb.toString();
	}

	private void dfsRecursive(int current, boolean[] visited, StringBuilder sb) {
		visited[current] = true;
		String name = arrDoc[current].getKey();
		sb.append(name).append(", ");

		TreeSet<String> toSort = new TreeSet<>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[current][i] > 0 && !visited[i]) {
				toSort.add(arrDoc[i].getKey());
			}
		}
		for (String nextName : toSort) {
			int nextIdx = name2Int.get(nextName);
			if (!visited[nextIdx]) {
				dfsRecursive(nextIdx, visited, sb);
			}
		}
	}

	public int connectedComponents() {
		DisjointSetForest dsf = new DisjointSetForest(arr.length);

		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i][j] > 0 || arr[j][i] > 0) {
					dsf.union(i, j);
				}
			}
		}
		return dsf.countSets();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("   ");
		for (int j = 0; j < arr.length; j++) {
			sb.append(String.format("%8s", arrDoc[j].getKey()));
		}
		sb.append("\n");

		for (int i = 0; i < arr.length; i++) {
			sb.append(String.format("%3s", arrDoc[i].getKey()));
			for (int j = 0; j < arr.length; j++) {
				sb.append(String.format("%8d", arr[i][j]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public String DijkstraSSSP(String start) {
		if (start == null) return null;
		Integer startIndex = name2Int.get(start.toLowerCase());
		if (startIndex == null) {
			return null;
		}

		int n = arr.length;
		int[] dist = new int[n];
		boolean[] visited = new boolean[n];
		int[] prev = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(prev, -1);
		dist[startIndex] = 0;

		for (int round = 0; round < n; round++) {
			int u = -1, bestDist = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				if (!visited[i] && dist[i] < bestDist) {
					bestDist = dist[i];
					u = i;
				}
			}
			if (u == -1) break;
			visited[u] = true;

			for (int v = 0; v < n; v++) {
				if (arr[u][v] > 0 && !visited[v]) {
					int alt = dist[u] + arr[u][v];
					if (alt < dist[v]) {
						dist[v] = alt;
						prev[v] = u;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < n; idx++) {
			String name = arrDoc[idx].getKey();
			if (idx == startIndex) {
				sb.append(name).append("=0\n");
			} else if (dist[idx] == Integer.MAX_VALUE) {
				sb.append("no path to ").append(name).append("\n");
			} else {

				LinkedList<String> path = new LinkedList<>();
				for (int at = idx; at != -1; at = prev[at]) {
					path.addFirst(arrDoc[at].getKey());
				}
				sb.append(String.join("->", path)).append("=").append(dist[idx]).append("\n");
			}
		}
		return sb.toString();
	}

}