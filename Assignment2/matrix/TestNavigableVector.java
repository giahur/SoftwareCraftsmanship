package matrix;

import static org.junit.Assert.assertEquals;
import java.util.NavigableMap;
import java.util.Map;
import org.junit.Test;
import java.util.TreeMap;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.NoSuchElementException;

public class TestNavigableVector {

    @Test
    public void testPeekingIterator() {
        NavigableMap<Integer, String> myMap = new TreeMap<>();
        myMap.put(1, "one");
        myMap.put(2, "two");
        myMap.put(3, "");
        myMap.put(4, "four");
        NavigableVector<String> myMapZero = NavigableVector.from(myMap, "");

        PeekingIterator<Map.Entry<Integer, String>> iterator = myMapZero.peekingIterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(null, iterator.peek());
        assertThrows(NoSuchElementException.class, () -> iterator.element());
        //assertTrue(iterator.hasNext());
        //assertEquals((Integer)2, iterator.peek().getValue());
        //assertEquals((Integer)3, iterator.element().getValue());
    }
    
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
