package matrix;

import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import matrix.NavigableMatrix.InvalidLengthException;

public class TestNavigableMatrix {

    private NavigableMatrix<Integer> myMatrix() {
        NavigableMap<Indexes, Integer> myMap = new TreeMap<>();
        myMap.put(new Indexes(0, 0), 1);
        myMap.put(new Indexes(0, 1), 2);
        myMap.put(new Indexes(1, 0), 3);
        return new NavigableMatrix<Integer>(myMap, 0);
    }

    @Test
    public void testValue() {
        NavigableMatrix<Integer> matrix = myMatrix();
        assertEquals((Integer)1, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)2, matrix.value(new Indexes(0, 1)));
        
        assertThrows(NullPointerException.class, () -> matrix.value(null));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.value(new Indexes(1, 1)));
    }

    @Test
    public void testZero() {
        NavigableMatrix<Integer> matrix = myMatrix();
        assertEquals((Integer)0, matrix.zero());
    }

    @Test
    public void testRepresentation() {
        NavigableMatrix<Integer> matrix = myMatrix();
        NavigableMap<Indexes, Integer> expectedMap = new TreeMap<>();
        expectedMap.put(new Indexes(0, 0), 1);
        expectedMap.put(new Indexes(0, 1), 2);
        expectedMap.put(new Indexes(1, 0), 3);
        assertEquals(expectedMap, matrix.representation());
    }

    @Test
    public void testPeekingIterator() {
        NavigableMatrix<Integer> matrix = myMatrix();
        PeekingIterator<Map.Entry<Indexes, Integer>> iterator = matrix.peekingIterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(null, iterator.peek());
        assertThrows(NoSuchElementException.class, () -> iterator.element());
        //assertTrue(iterator.hasNext());
        //assertEquals((Integer)2, iterator.peek().getValue());
        //assertEquals((Integer)3, iterator.element().getValue());
    }

    @Test
    public void testInvalidLengthException() {
        InvalidLengthException exception = new InvalidLengthException(InvalidLengthException.Cause.ROW, 1);
        assertEquals(InvalidLengthException.Cause.ROW, exception.cause());
        assertEquals(1, exception.length());
        assertEquals(1, exception.requireNonEmpty(InvalidLengthException.Cause.ROW, 1));
        assertThrows(NullPointerException.class, () -> exception.requireNonEmpty(null, 1));
        assertThrows(IllegalArgumentException.class, () -> exception.requireNonEmpty(InvalidLengthException.Cause.ROW, -1));
    }

    @Test
    public void testInstance() {
        NavigableMatrix<Integer> matrix = NavigableMatrix.instance(2, 2, indexes -> indexes.row(), 0);
        assertEquals((Integer)0, matrix.value(new Indexes(0, 1)));
        assertEquals((Integer)1, matrix.value(new Indexes(1, 0)));
        
        assertThrows(NullPointerException.class, () -> NavigableMatrix.instance(0, 1, null, 0));
        assertThrows(NullPointerException.class, () -> NavigableMatrix.instance(0, 1, indexes -> indexes.row(), null));
        assertThrows(IllegalArgumentException.class, () -> NavigableMatrix.instance(0, 1, indexes -> indexes.row(), 0));
    }

    @Test
    public void testConstant() {
        NavigableMatrix<Integer> matrix = NavigableMatrix.constant(2, 2, 1, 0);
        assertEquals((Integer)1, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)1, matrix.value(new Indexes(1, 0)));
        
        assertThrows(NullPointerException.class, () -> NavigableMatrix.constant(0, 1, null, 0));
        assertThrows(NullPointerException.class, () -> NavigableMatrix.constant(0, 1, 1, null));
        assertThrows(IllegalArgumentException.class, () -> NavigableMatrix.constant(0, 1, 4, 0));
    }

    @Test
    public void testIdentity() {
        NavigableMatrix<Integer> matrix = NavigableMatrix.identity(2, 0, 1);
        assertEquals((Integer)1, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)0, matrix.value(new Indexes(1, 0)));
        
        assertThrows(NullPointerException.class, () -> NavigableMatrix.identity(2, null, 4));
        assertThrows(NullPointerException.class, () -> NavigableMatrix.identity(2, 1, null));
        assertThrows(IllegalArgumentException.class, () -> NavigableMatrix.identity(0, 1, 4));
    }

    @Test
    public void testFromNavigableMap() {
        NavigableMap<Indexes, Integer> map = new TreeMap<>();
        map.put(new Indexes(0, 0), 1);
        map.put(new Indexes(1, 1), 2);
        NavigableMatrix<Integer> matrix = NavigableMatrix.from(map, 0);
        assertEquals((Integer)1, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)2, matrix.value(new Indexes(1, 1)));

        NavigableMap<Indexes, Integer> nullMap = null;
        assertThrows(NullPointerException.class, () -> NavigableMatrix.from(nullMap, 0));
        assertThrows(NullPointerException.class, () -> NavigableMatrix.from(map, null));
    }

    @Test
    public void testFromArray() {
        Integer[][] array = {{1, 2, 3}, {4, 5, 6}};
        NavigableMatrix<Integer> matrix = NavigableMatrix.from(array, 0);
        assertEquals((Integer)4, matrix.value(new Indexes(1, 0)));

        Integer[][] nullArray = null;
        assertThrows(NullPointerException.class, () -> NavigableMatrix.from(nullArray, 0));
        assertThrows(NullPointerException.class, () -> NavigableMatrix.from(array, null));
    }

    
    }