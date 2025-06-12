// DisjointSetDataStructure.java
package dsaa.lab11;

/**
 * A standard Disjoint-Set (“Union-Find”) interface.
 * Elements are integer values from 0 to N-1.
 */
public interface DisjointSetDataStructure {
	/**
	 * Create a singleton set containing exactly {@code item}.
	 * @param item an integer between 0 and N-1 (inclusive)
	 */
	void makeSet(int item);

	/**
	 * Return the representative (“root”) of the set containing {@code item},
	 * with path-compression applied.
	 * @param item an integer between 0 and N-1 (inclusive)
	 * @return the representative of item’s set
	 */
	int findSet(int item);

	/**
	 * Unite (union) the two sets that contain {@code itemA} and {@code itemB}.
	 * Uses rank-by-union to keep trees shallow.
	 * @param itemA an integer between 0 and N-1
	 * @param itemB an integer between 0 and N-1
	 * @return {@code true} if they were previously disjoint and are now merged;
	 *         {@code false} if they were already in the same set.
	 */
	boolean union(int itemA, int itemB);

	/**
	 * Count how many disjoint sets currently exist.
	 * @return number of distinct sets
	 */
	int countSets();
}
