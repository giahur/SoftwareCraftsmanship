package matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.List;

import org.junit.Test;

public class TestIndexes {

    Indexes myOrigin = Indexes.ORIGIN;
    Indexes index1 = new Indexes(2, 1);
    Indexes index2 = new Indexes(1, 3);
    Indexes index3 = new Indexes(3, 1);
    Indexes index4 = new Indexes(3, 3);
    Indexes index5 = new Indexes(3, 5);

    @Test
    public void testWithRowColumn() {
        assertEquals(new Indexes(1, 1), index1.withRow(1));
        assertEquals(new Indexes(2, 1), index1.withRow(2));
        assertEquals(new Indexes(3, 4), index3.withColumn(4));
        assertEquals(new Indexes(3, 1), index3.withColumn(1));
    }

    @Test
    public void testByRowsColumns() {
        assertThrows(NullPointerException.class, () -> index1.compareTo(null));
        assertTrue(index1.compareTo(index2) > 0);
        assertTrue(index3.compareTo(index4) < 0);
        assertTrue(index1.compareTo(index1) == 0);

        assertTrue(Indexes.byRows.compare(index1, index2) > 0);
        assertTrue(Indexes.byRows.compare(index2, index4) < 0);
        assertTrue(Indexes.byRows.compare(index1, index1) == 0);
        assertTrue(Indexes.byRows.compare(myOrigin, index1) < 0);

        assertTrue(Indexes.byColumns.compare(index1, index2) < 0);
        assertTrue(Indexes.byColumns.compare(index2, index4) < 0);
        assertTrue(Indexes.byColumns.compare(index1, index1) == 0);
        assertTrue(Indexes.byColumns.compare(myOrigin, index1) < 0);
    }

    @Test
    public void testValueArray() {
        Integer[][] myMatrix = {{0, 1, 2, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        assertEquals(Integer.valueOf(10),index1.value(myMatrix));
        assertEquals(Integer.valueOf(8),index2.value(myMatrix));
        assertEquals(Integer.valueOf(0), myOrigin.value(myMatrix));

        Integer[][] nullMatrix = null;
        assertThrows(NullPointerException.class, () -> index1.value(nullMatrix));
    }

    @Test
    public void testValueMatrix() {
        Integer[][] myMatrix = {{1, 1, 2, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        NavigableMatrix<Integer> matrix = NavigableMatrix.from(myMatrix, 0);
        assertEquals(Integer.valueOf(10),index1.value(matrix));
        assertEquals(Integer.valueOf(8),index2.value(matrix));
        assertEquals(Integer.valueOf(1), myOrigin.value(matrix));

        NavigableMatrix<Integer> nullMatrix = null;
        assertThrows(NullPointerException.class, () -> index1.value(nullMatrix));
    }

    @Test
    public void testDiagonal() {
        assertFalse(index1.areDiagonal());
        assertTrue(index4.areDiagonal());
    }

    @Test
    public void testStream() {
        //stream(Indexes from, Indexes to)
        assertThrows(NullPointerException.class, () -> Indexes.stream(null, index1));
        assertThrows(NullPointerException.class, () -> Indexes.stream(index1, null));
        List<Indexes> indexesList1 = Indexes.stream(index2, index5).toList();
        assertEquals(List.of(new Indexes(1, 3), new Indexes(1, 4), new Indexes(2, 3), new Indexes(2, 4)), indexesList1);

        //stream(Indexes size)
        assertThrows(NullPointerException.class, () -> Indexes.stream(null));
        List<Indexes> indexesList2 = Indexes.stream(index1).toList();
        assertEquals(List.of(new Indexes(0, 0), new Indexes(1, 0)), indexesList2);

        //stream(int row, int columns)
        List<Indexes> indexesList3 = Indexes.stream(2,2).toList();
        assertEquals(List.of(new Indexes(0, 0), new Indexes(0, 1), new Indexes(1, 0), new Indexes(1, 1)), indexesList3);
    }
}