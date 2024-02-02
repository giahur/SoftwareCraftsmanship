package matrix;

import static org.junit.Assert.assertEquals;
import java.util.NavigableMap;
import org.junit.Test;
import java.util.TreeMap;
import static org.junit.Assert.assertThrows;

public class TestNavigableVector {
    @Test
    public void testFrom() {
        NavigableMap<Integer, String> myMap = new TreeMap<>();
        myMap.put(1, "one");
        myMap.put(2, "two");
        myMap.put(3, "");
        myMap.put(4, "four");
        NavigableVector<String> myMapZero = NavigableVector.from(myMap, "");

        NavigableMap<Integer, String> expectedMap = new TreeMap<>();
        expectedMap.put(1, "one");
        expectedMap.put(2, "two");
        expectedMap.put(4, "four");

        assertEquals(expectedMap, myMapZero.representation());
        assertEquals("", myMapZero.zero());
        assertEquals("two", myMapZero.value(2));

        assertThrows(NullPointerException.class, () -> NavigableVector.from(null, 0));
        assertThrows(NullPointerException.class, () -> NavigableVector.from(myMap, null));
    }
}
