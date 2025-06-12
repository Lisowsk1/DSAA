package dsaa.lab11;

public class Link implements Comparable<Link> {
	final String ref;
	final int weight;

	public Link(String ref) {
		this(ref, 1);
	}

	public Link(String ref, int weight) {
		if (ref == null || ref.isEmpty()) {
			throw new IllegalArgumentException("Link reference must not be empty");
		}
		this.ref = ref.toLowerCase();
		this.weight = weight;
	}

	public String getRef() {
		return ref;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Link)) return false;
		Link other = (Link) obj;

		return this.ref.equalsIgnoreCase(other.ref);
	}

	@Override
	public int hashCode() {
		return ref.toLowerCase().hashCode();
	}

	@Override
	public String toString() {
		return ref + "(" + weight + ")";
	}

	@Override
	public int compareTo(Link other) {

		return this.ref.compareTo(other.ref);
	}
}