import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import java.lang.reflect.*;

public class OptimalThingsPublicTest {
	// ========== SYSTEM ==========
	protected static final String EX_NAME_MIN_MAX = "min,max";
	protected static final String EX_NAME_PARETO = "pareto";
	protected static final String METHOD_MIN_NAT = "min()";
	protected static final String METHOD_MAX_NAT = "max()";
	protected static final String METHOD_MIN_CMP = "min(cmp)";
	protected static final String METHOD_MAX_CMP = "max(cmp)";
	protected static final String METHOD_PARETO = "pareto()";
	// --------------------

	// ========== TEST DATA AND HELPERS ==========
	private static final ThingComparator<StringThing> stringThingComparator = new StringThingComparator();
	private static final List<StringThing> stringThingData = generateStringThings("C", "AuD", "Ei", "DAU", "Argh", "Ga", "Foo", "Dumbo", "BlaBla");
	private static final ThingComparator<DoubleLongThing> doubleLongThingComparator = new DoubleLongThingComparator();
	private static final List<DoubleLongThing> doubleLongThingData = generateDoubleLongThings(new long[][]{{1, 3}, {3, 1}, {2, 5}, {3, 4}, {4, 1}, {2, 7}, {3, 6}, {5, 4}, {6, 2}});

	// ========== PUBLIC TEST ==========
	// ---------- Intestines ----------------------------------------------------------------------
	@Test(timeout = 666)
	public void pubTest__intestines_THIS_TEST_IS_VERY_IMPORTANT_IF_IT_FAILS_THEN_YOU_WILL_GET_NO_POINTS_AT_ALL() {
		@SuppressWarnings("rawtypes") Class<OptimalThings> clazz = OptimalThings.class;
		assertTrue(clazz + " must be public!", Modifier.isPublic(clazz.getModifiers()));
		assertFalse(clazz + " must not be abstract!", Modifier.isAbstract(clazz.getModifiers()));
		assertFalse(clazz + " must not be an annotation!", clazz.isAnnotation());
		assertFalse(clazz + " must not be an enumeration!", clazz.isEnum());
		assertFalse(clazz + " must not be an interface!", clazz.isInterface());
		assertSame(clazz + " must extend a certain super-class!", Object.class, clazz.getSuperclass());
		assertEquals(clazz + " must implement a certain number of interfaces!", 0, clazz.getInterfaces().length);
		assertEquals(clazz + " must declare a certain number of inner annotations!", 0, clazz.getDeclaredAnnotations().length);
		assertEquals(clazz + " must declare a certain number of inner classes!", 0, getDeclaredClasses(clazz).length);
		Field[] fields = getDeclaredFields(clazz);
		assertEquals(clazz + " must declare a certain number of fields!", 1, fields.length);
		for (Field field : fields) {
			assertSame(field + " - field must have a certain type!", List.class, field.getType());
		}
		Constructor<?>[] constructors = getDeclaredConstructors(clazz);
		assertEquals(clazz + " must declare a certain number of constructors (possibly including default constructor)!", 1, constructors.length);
		for (Constructor<?> constructor : constructors) {
			assertTrue(constructor + " - constructor must be public!", Modifier.isPublic(constructor.getModifiers()));
			assertEquals(constructor + " - constructor must have a certain number of parameters!", 1, constructors[0].getParameterTypes().length);
			assertSame(constructor + " - constructor must have parameters of certain types!", List.class, constructors[0].getParameterTypes()[0]);
		}
		Method[] methods = getDeclaredMethods(clazz);
		assertEquals(clazz + " must declare a certain number of methods!", 5, methods.length);
		for (Method method : methods) {
			assertTrue(method + " - method must be public!", Modifier.isPublic(method.getModifiers()));
			assertFalse(method + " - method must not be static!", Modifier.isStatic(method.getModifiers()));
			assertFalse(method + " - method must not be abstract!", Modifier.isAbstract(method.getModifiers()));
		}
	}

	// ---------- Extrema ----------------------------------------------------------------------
	@Test(timeout = 666)
	public void pubTest__min__max__STRING_THING() {
		List<StringThing> stringThingList = shuffleAndFreeze(stringThingData);
		OptimalThings<StringThing> optimalThings = new OptimalThings<>(stringThingList);
		StringThing minComparable = optimalThings.min(), maxComparable = optimalThings.max();
		assertSame(OptimalThingsPublicTest.METHOD_MIN_NAT, stringThingData.get(0), minComparable);
		assertSame(OptimalThingsPublicTest.METHOD_MAX_NAT, stringThingData.get(8), maxComparable);
		StringThing minComparator = optimalThings.min(stringThingComparator), maxComparator = optimalThings.max(stringThingComparator);
		assertTrue(OptimalThingsPublicTest.METHOD_MIN_CMP, minComparator == stringThingData.get(1) || minComparator == stringThingData.get(4));
		assertSame(OptimalThingsPublicTest.METHOD_MAX_CMP, stringThingData.get(5), maxComparator);
	}

	@Test(timeout = 666)
	public void pubTest__min__max__DOUBLE_LONG_THING() {
		List<DoubleLongThing> doubleLongThingList = shuffleAndFreeze(doubleLongThingData);
		OptimalThings<DoubleLongThing> optimalThings = new OptimalThings<>(doubleLongThingList);
		DoubleLongThing minComparable = optimalThings.min(), maxComparable = optimalThings.max();
		assertSame(OptimalThingsPublicTest.METHOD_MIN_NAT, doubleLongThingData.get(0), minComparable);
		assertSame(OptimalThingsPublicTest.METHOD_MAX_NAT, doubleLongThingData.get(8), maxComparable);
		DoubleLongThing minComparator = optimalThings.min(doubleLongThingComparator), maxComparator = optimalThings.max(doubleLongThingComparator);
		assertTrue(OptimalThingsPublicTest.METHOD_MIN_CMP, minComparator == doubleLongThingData.get(1) || minComparator == doubleLongThingData.get(4));
		assertSame(OptimalThingsPublicTest.METHOD_MAX_CMP, doubleLongThingData.get(5), maxComparator);
	}

	// ---------- Pareto ----------------------------------------------------------------------
	@Test(timeout = 666)
	public void pubTest__pareto__STRING_THING() {
		List<StringThing> stringThingList = shuffleAndFreeze(stringThingData);
		LinkedList<List<StringThing>> expected = new LinkedList<>();
		for (int i = 0; i < stringThingData.size(); i++) {
			if (i == 0 || i == 2 || i == 5) expected.addFirst(new LinkedList<StringThing>());
			expected.getFirst().add(stringThingData.get(i));
		}
		OptimalThingsPublicTest.check__pareto(stringThingList, stringThingComparator, expected);
	}

	@Test(timeout = 666)
	public void pubTest__pareto__DOUBLE_LONG_THING() {
		List<DoubleLongThing> doubleLongThingList = shuffleAndFreeze(doubleLongThingData);
		LinkedList<List<DoubleLongThing>> expected = new LinkedList<>();
		for (int i = 0; i < doubleLongThingData.size(); i++) {
			if (i == 0 || i == 2 || i == 5) expected.addFirst(new LinkedList<DoubleLongThing>());
			expected.getFirst().add(doubleLongThingData.get(i));
		}
		OptimalThingsPublicTest.check__pareto(doubleLongThingList, doubleLongThingComparator, expected);
	}

	// ========== HELPER ==========
	protected static <T> java.util.List<T> shuffleAndFreeze(java.util.List<T> thingList) {
		java.util.List<T> thingListClone = new java.util.LinkedList<>(thingList);
		Collections.shuffle(thingListClone);
		return Collections.unmodifiableList(thingListClone);
	}

	protected static <T extends ComparableThing<T>> void check__pareto(List<T> things, ThingComparator<T> tc, List<List<T>> expected) {
		OptimalThings<T> optimalThings = new OptimalThings<>(things);
		List<List<T>> actual = optimalThings.pareto(tc);
		assertNotNull(OptimalThingsPublicTest.METHOD_PARETO + " illegally returned null.", actual);
		assertEquals(OptimalThingsPublicTest.METHOD_PARETO + " returned wrong number of pareto fronts.", expected.size(), actual.size());
		for (int frontIdx = 0; frontIdx < actual.size(); frontIdx++) {
			assertEquals(OptimalThingsPublicTest.METHOD_PARETO + " returned wrong number of elements in pareto front " + frontIdx + ".", expected.get(frontIdx).size(), actual.get(frontIdx).size());
			for (int thingIdx = 0; thingIdx < actual.get(frontIdx).size(); thingIdx++) {
				assertTrue(OptimalThingsPublicTest.METHOD_PARETO + " returned an unexpected element in front " + frontIdx + " at index " + thingIdx + ".", expected.get(frontIdx).contains(actual.get(frontIdx).get(thingIdx)));
				assertTrue(OptimalThingsPublicTest.METHOD_PARETO + " missed an element in front " + frontIdx + ".", actual.get(frontIdx).contains(expected.get(frontIdx).get(thingIdx)));
			}
		}
	}

	// ---------- example: DoubleLongThing ----------------------------------------------------------------------
	private static final class DoubleLongThing implements ComparableThing<DoubleLongThing> {
		private final long x, y;

		private DoubleLongThing(long x, long y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean isLessThan(DoubleLongThing other) {
			return x < other.x;
		}

		@Override
		public String toString() {
			return null;
		}
	}

	private static final class DoubleLongThingComparator implements ThingComparator<DoubleLongThing> {
		@Override
		public boolean isLess(DoubleLongThing o1, DoubleLongThing o2) {
			return o1.y < o2.y;
		}
	}

	private static java.util.List<DoubleLongThing> generateDoubleLongThings(long[]... ls) {
		java.util.List<DoubleLongThing> doubleLongThingList = new java.util.LinkedList<>();
		for (long[] dl : ls) {
			doubleLongThingList.add(new DoubleLongThing(dl[0], dl[1]));
		}
		return doubleLongThingList;
	}

	// ---------- example: StringThing ----------------------------------------------------------------------
	private static final class StringThing implements ComparableThing<StringThing> {
		private final String s;

		private StringThing(String s) {
			this.s = s;
		}

		@Override
		public boolean isLessThan(StringThing other) {
			return s.length() < other.s.length();
		}

		@Override
		public String toString() {
			return null;
		}
	}

	private static final class StringThingComparator implements ThingComparator<StringThing> {
		@Override
		public boolean isLess(StringThing o1, StringThing o2) {
			return o1.s.charAt(0) < o2.s.charAt(0);
		}
	}

	private static java.util.List<StringThing> generateStringThings(String... ss) {
		java.util.List<StringThing> stringThingList = new java.util.LinkedList<>();
		for (String s : ss) {
			stringThingList.add(new StringThing(s));
		}
		return stringThingList;
	}

	// ========== HELPER: Intestines ==========
	// @AuD-STUDENT: DO NOT USE REFLECTION IN YOUR OWN SUBMISSION!
	private static Class<?>[] getDeclaredClasses(Class<?> clazz) {
		java.util.List<Class<?>> declaredClasses = new java.util.ArrayList<>();
		for (Class<?> c : clazz.getDeclaredClasses()) {
			if (!c.isSynthetic()) {
				declaredClasses.add(c);
			}
		}
		return declaredClasses.toArray(new Class[0]);
	}

	private static Field[] getDeclaredFields(Class<?> clazz) {
		java.util.List<Field> declaredFields = new java.util.ArrayList<>();
		for (Field f : clazz.getDeclaredFields()) {
			if (!f.isSynthetic()) {
				declaredFields.add(f);
			}
		}
		return declaredFields.toArray(new Field[0]);
	}

	private static Constructor<?>[] getDeclaredConstructors(Class<?> clazz) {
		java.util.List<Constructor<?>> declaredConstructors = new java.util.ArrayList<>();
		for (Constructor<?> c : clazz.getDeclaredConstructors()) {
			if (!c.isSynthetic()) {
				declaredConstructors.add(c);
			}
		}
		return declaredConstructors.toArray(new Constructor[0]);
	}

	private static Method[] getDeclaredMethods(Class<?> clazz) {
		java.util.List<Method> declaredMethods = new java.util.ArrayList<>();
		for (Method m : clazz.getDeclaredMethods()) {
			if (!m.isBridge() && !m.isSynthetic()) {
				declaredMethods.add(m);
			}
		}
		return declaredMethods.toArray(new Method[0]);
	}
}