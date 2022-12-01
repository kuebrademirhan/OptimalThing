// comparable to https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Comparable.html
public interface ComparableThing<T> {
	/**
	 * Compares this object with the specified other object for order. Returns true iff this object is less than the specified other object.
	 *
	 * @param other the object to be compared.
	 * @return true iff this object is less than the specified other object.
	 */
	boolean isLessThan(T other);
}