// comparable to https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Comparator.html
public interface ThingComparator<T> {
	/**
	 * Compares its two arguments for order. Returns true iff the first argument is less than the second.
	 *
	 * @param o1 the first object to be compared.
	 * @param o2 the second object to be compared.
	 * @return true iff the first argument is less than the second.
	 */
	boolean isLess(T o1, T o2);
}