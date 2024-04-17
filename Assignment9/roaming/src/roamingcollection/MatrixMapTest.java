package roaming.src.roamingcollection;

import org.junit.Test;

import roaming.src.roamingcollection.MatrixMap.InvalidLengthException;

import static org.junit.Assert.*;

// Test class for MatrixMap
public class MatrixMapTest {

    @Test
    public void requireNonEmptyTest() {
        //Test 1 (CC1, b1): length < 0
        assertThrows(IllegalArgumentException.class, () -> InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, -1));
        //Test 2 (b2): length = 0
        assertThrows(IllegalArgumentException.class, () -> InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, 0));
        //Test 3 (BC1, b3): length > 0
        assertEquals(1, InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, 1));
    }

    @Test
    public void instanceRowColTest() {
        //Test 1 (CC1, GD1): nonnull valueMapper
        MatrixMap<Integer> matrix = MatrixMap.instance(2, 2, (index) -> 3);
        assertEquals((Integer)3, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(0, 1)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 1)));
        //Test 2 (BD1): null valueMapper
        assertThrows(NullPointerException.class, () -> MatrixMap.instance(2, 2, null));
    }

    @Test
    public void instantSizeTest() {
        //Test 1 (CC1, GD1, GD2): nonnull size, nonnull valueMapper
        MatrixMap<Integer> matrix = MatrixMap.instance(new Indexes(2,2), (index) -> 3);
        assertEquals((Integer)3, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(0, 1)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 1)));
        //Test 2 (BD1): null size
        assertThrows(NullPointerException.class, () -> MatrixMap.instance(null, (index) -> 3));
        //Test 3 (BD2): null valueMapper
        assertThrows(NullPointerException.class, () -> MatrixMap.instance(new Indexes(2,2), null));
    }

    @Test
    public void constantTest() {
        //Test 1 (CC1, GD1): nonnull value
        MatrixMap<Integer> matrix = MatrixMap.constant(2, 3);
        assertEquals((Integer)3, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(0, 1)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 1)));
        //Test 2 (BD1): null value
        assertThrows(NullPointerException.class, () -> MatrixMap.constant(2, null));
    }

    @Test
    public void identityTest() {
        //Test 1 (CC1, CC2, CC3, B1, B2, GD1, GD2): nonnull zero, nonnull identity, indexes are diagonal, are not diagonal
        MatrixMap<Integer> matrix = MatrixMap.identity(2, 0, 3);
        assertEquals((Integer)3, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)0, matrix.value(new Indexes(0, 1)));
        assertEquals((Integer)0, matrix.value(new Indexes(1, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 1)));
        //Test 2 (BD1): null zero
        assertThrows(NullPointerException.class, () -> MatrixMap.identity(2, null, 3));
        //Test 3 (BD2): null identity
        assertThrows(NullPointerException.class, () -> MatrixMap.identity(2, 0, null));        
    }

    @Test
    public void fromTest() {
        //Test 1 (CC1, GD1, GD2): matrix nonnull, row and column valid length
        Integer[][] array = {{1, 2, 3}, {4, 5, 6}};
        MatrixMap<Integer> matrix = MatrixMap.from(array);
        assertEquals((Integer)1, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)2, matrix.value(new Indexes(0, 1)));
        assertEquals((Integer)3, matrix.value(new Indexes(0, 2)));
        assertEquals((Integer)4, matrix.value(new Indexes(1, 0)));
        assertEquals((Integer)5, matrix.value(new Indexes(1, 1)));
        assertEquals((Integer)6, matrix.value(new Indexes(1, 2)));
        //Test 2 (BD1): matrix null
        Integer[][] nullArray = null;
        assertThrows(NullPointerException.class, () -> MatrixMap.from(nullArray));
        //Test 3 (BD2): row invalid length
        assertThrows(IllegalArgumentException.class, () -> MatrixMap.from(new Integer [0][3]));
        //Test 4 (BD3): column invalid length
        assertThrows(IllegalArgumentException.class, () -> MatrixMap.from(new Integer [3][0]));
    }

    @Test
    public void sizeTest() {
        //Test 1 (CC1, CC2, BC1, b1, b2): iterator has next, size.compareTo(currentIndex) > 0, = 0
        Integer[][] array1 = {{1, 2}, {3, 4}};
        MatrixMap<Integer> matrix1 = MatrixMap.from(array1);
        assertEquals(new Indexes (2,2), matrix1.size());
        //Test 2: iterator does not have next
        Integer[][] array2 = {{1}};
        MatrixMap<Integer> matrix2 = MatrixMap.from(array2);
        assertEquals(new Indexes (1,1), matrix2.size());
    }

    @Test
    public void toStringTest() {
        Integer[][] array1 = {{1, 2}, {3, 4}};
        MatrixMap<Integer> matrix1 = MatrixMap.from(array1);        
        assertEquals("{Indexes[row=0, column=0]=1, Indexes[row=1, column=1]=4, Indexes[row=0, column=1]=2, Indexes[row=1, column=0]=3}", matrix1.toString());

    }
    
    @Test
    public void valueTest() {
        //Test 1 (CC1, GD1): nonnull indexes
        MatrixMap<Integer> matrix = MatrixMap.instance(2, 2, (index) -> 3);
        assertEquals((Integer)3, matrix.value(new Indexes(0, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(0, 1)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 0)));
        assertEquals((Integer)3, matrix.value(new Indexes(1, 1)));
        assertEquals((Integer)3, matrix.value(0, 0));
        assertEquals((Integer)3, matrix.value(0, 1));
        assertEquals((Integer)3, matrix.value(1, 0));
        assertEquals((Integer)3, matrix.value(1, 1));
        //Test 2 (BD1): null indexes
        assertThrows(NullPointerException.class, () -> matrix.value(null));  
    }

    @Test
    public void buildMatrixTest() {
        //Test 1 (CC1): nonempty row and column
        RoamingMap<Indexes, Integer> roamingMap = MatrixMap.Test.buildMatrix(2, 2, (index) -> 3);
        assertEquals((Integer)3, roamingMap.get(new Indexes(0, 0)));
        assertEquals((Integer)3, roamingMap.get(new Indexes(0, 1)));
        assertEquals((Integer)3, roamingMap.get(new Indexes(1, 0)));
        assertEquals((Integer)3, roamingMap.get(new Indexes(1, 1)));
        //Test 2 (BD1): empty row
        assertThrows(IllegalArgumentException.class, () -> MatrixMap.Test.buildMatrix(0, 3, (index) -> 3));
        //Test 3 (BD2): empty column
        assertThrows(IllegalArgumentException.class, () -> MatrixMap.Test.buildMatrix(3, 0, (index) -> 3));
        //Test 4 (BD3): null valueMapper
        assertThrows(NullPointerException.class, () -> MatrixMap.Test.buildMatrix(3, 3, null));
    }
}