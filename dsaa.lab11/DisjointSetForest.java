package dsaa.lab11;

public class DisjointSetForest implements DisjointSetDataStructure {

	private static class Element {
		int parent;
		int rank;
	}

	private final Element[] arr;

	public DisjointSetForest(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size must be positive.");
		}
		this.arr = new Element[size];

		for (int i = 0; i < size; i++) {
			makeSet(i);
		}
	}

	@Override
	public void makeSet(int item) {
		if (item < 0 || item >= arr.length) {
			throw new IllegalArgumentException("Index out of bounds: " + item);
		}
		Element e = new Element();
		e.parent = item;
		e.rank = 0;
		arr[item] = e;
	}

	@Override
	public int findSet(int item) {
		if (item < 0 || item >= arr.length || arr[item] == null) {
			throw new IllegalArgumentException("Invalid item: " + item);
		}
		if (arr[item].parent != item) {
			arr[item].parent = findSet(arr[item].parent);
		}
		return arr[item].parent;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		if (itemA < 0 || itemA >= arr.length || arr[itemA] == null ||
				itemB < 0 || itemB >= arr.length || arr[itemB] == null) {
			throw new IllegalArgumentException("Invalid items: " + itemA + ", " + itemB);
		}
		int rootA = findSet(itemA);
		int rootB = findSet(itemB);
		if (rootA == rootB) {
			return false;
		}

		if (arr[rootA].rank < arr[rootB].rank) {
			arr[rootA].parent = rootB;
		} else if (arr[rootA].rank > arr[rootB].rank) {
			arr[rootB].parent = rootA;
		} else {

			arr[rootB].parent = rootA;
			arr[rootA].rank++;
		}
		return true;
	}

	@Override
	public int countSets() {
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null && arr[i].parent == i) {
				count++;
			}
		}
		return count;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("DisjointSetForest:\n");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				sb.append("  ").append(i)
						.append(" -> ").append(arr[i].parent)
						.append("  (rank=").append(arr[i].rank).append(")\n");
			} else {
				sb.append("  ").append(i).append(" -> null\n");
			}
		}
		return sb.toString().trim();
	}
}