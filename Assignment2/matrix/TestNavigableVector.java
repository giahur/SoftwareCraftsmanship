package matrix;

import static org.junit.Assert.assertEquals;
import java.util.NavigableMap;
import java.util.Map;
import org.junit.Test;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TestNavigableVector {

    @Test 
    public void testMerge() {
        NavigableMap<Integer, String> myMap1 = new TreeMap<>();
        myMap1.put(1, "a");
        myMap1.put(2, "b");
        myMap1.put(3, "");
        NavigableVector<String> myVector1 = NavigableVector.from(myMap1, "");

        NavigableMap<Integer, String> myMap2 = new TreeMap<>();
        myMap2.put(1, "c");
        myMap2.put(2, "");
        myMap2.put(3, "d");
        NavigableVector<String> myVector2 = NavigableVector.from(myMap2, "");
        
        BinaryOperator<String> op = (x, y) -> x + y;
                
        Matrix<Integer, String> addedVectors = myVector1.merge(myVector2, op);
        
        assertEquals("ac", addedVectors.value(1));
        assertEquals("b", addedVectors.value(2));
        assertEquals("d", addedVectors.value(3));
    }

    @Test
    public void testPeekingIterator() {
        NavigableMap<Integer, String> myMap = new TreeMap<>();
        myMap.put(1, "one");
        myMap.put(2, "two");
        myMap.put(3, "");
        NavigableVector<String> myMapZero = NavigableVector.from(myMap, "");

        PeekingIterator<Map.Entry<Integer, String>> iterator = myMapZero.peekingIterator();
        assertTrue(iterator.hasNext());
        assertEquals("one", iterator.peek().get().getValue());
        assertEquals("one", iterator.next().getValue());
        assertEquals("two", iterator.element().getValue());
        assertTrue(iterator.hasNext());
        assertEquals("two", iterator.next().getValue());
        assertFalse(iterator.hasNext());
        
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(Optional.empty(), iterator.peek());
        assertThrows(NoSuchElementException.class, () -> iterator.element());
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
