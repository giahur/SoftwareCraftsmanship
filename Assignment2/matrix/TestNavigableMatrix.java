package matrix;

import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        assertTrue(iterator.hasNext());
        assertEquals((Integer)1, iterator.next().getValue());
        assertEquals((Integer)2, iterator.peek().get().getValue());
        assertEquals((Integer)2, iterator.next().getValue());
        assertEquals((Integer)3, iterator.element().getValue());
        assertTrue(iterator.hasNext());
        assertEquals((Integer)3, iterator.next().getValue());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(Optional.empty(), iterator.peek());
        assertThrows(NoSuchElementException.class, () -> iterator.element());
    }

    @Test
    public void testMerge() {
        NavigableMap<Indexes, String> myMap1 = new TreeMap<> ();
        myMap1.put(new Indexes (0,0), "a"); 
        myMap1.put(new Indexes (0, 1), "b"); 
        myMap1.put(new Indexes (1, 0), "c"); 
        myMap1.put(new Indexes (1, 1), "d");
        
        NavigableMatrix<String> myMatrix1 = NavigableMatrix.from(myMap1, "0") ;

        NavigableMap<Indexes, String> myMap2 = new TreeMap<> ();
        myMap2.put(new Indexes (0,0), "e"); 
        myMap2.put(new Indexes (0, 1), "0") ; 
        myMap2.put(new Indexes (1, 0), "f"); 
        myMap2.put(new Indexes (1, 1), "g");
        
        NavigableMatrix<String> myMatrix2 = NavigableMatrix.from(myMap2, "0") ;

        BinaryOperator<String> op = (x, y) -> x + y;
                
        Matrix<Indexes, String> addedMatrixes = myMatrix1.merge(myMatrix2, op);
        
        assertEquals("ae", addedMatrixes.value(new Indexes(0, 0)));
        assertEquals("b0", addedMatrixes.value(new Indexes(0, 1)));
        assertEquals("cf", addedMatrixes.value(new Indexes(1, 0)));
        assertEquals("dg", addedMatrixes.value(new Indexes(1, 1)));

        NavigableMap<Indexes, Integer> myMap3 = new TreeMap<> ();
        myMap3.put(new Indexes (0,0), 1); 
        myMap3.put(new Indexes (0, 1), 2); 
        
        NavigableMatrix<Integer> myMatrix3 = NavigableMatrix.from(myMap3, 0) ;

        NavigableMap<Indexes, Integer> myMap4 = new TreeMap<> ();
        myMap4.put(new Indexes (0, 0), 0); 
        myMap4.put(new Indexes (0, 1), 5); 
        
        NavigableMatrix<Integer> myMatrix4 = NavigableMatrix.from(myMap4, 0) ;

        BinaryOperator<Integer> op2 = (x, y) -> x + y;
                
        Matrix<Indexes, Integer> addedMatrixes2 = myMatrix3.merge(myMatrix4, op2);
        
        assertEquals((Integer)1, addedMatrixes2.value(new Indexes(0, 0)));
        assertEquals((Integer)7, addedMatrixes2.value(new Indexes(0, 1)));
    }

    @Test
    public void testInvalidLengthException() {
        InvalidLengthException exception = new InvalidLengthException(InvalidLengthException.Cause.ROW, 1);
        assertEquals(InvalidLengthException.Cause.ROW, exception.cause());
        assertEquals(1, exception.length());
        assertEquals(1, InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, 1));
        assertThrows(NullPointerException.class, () -> InvalidLengthException.requireNonEmpty(null, 1));
        assertThrows(IllegalArgumentException.class, () -> InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, -1));
    }

    @Test
    public void testInstance() {
        NavigableMatrix<Integer> matrix = NavigableMatrix.instance(2, 2, indexes -> indexes.row(), 0);
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