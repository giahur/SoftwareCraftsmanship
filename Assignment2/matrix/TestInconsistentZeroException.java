package matrix;

import static org.junit.Assert.assertThrows;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.Test;

public class TestInconsistentZeroException {
    @Test
    public void testrequireMatching() {

        NavigableMap<Indexes, Integer> mapInt = new TreeMap<>();
        mapInt.put(new Indexes(0,0), 1);

        Matrix<Indexes, Integer> matrix1 = new NavigableMatrix<>(mapInt, (Integer)2);
        Matrix<Indexes, Integer> matrix2 = new NavigableMatrix<>(mapInt, (Integer)3);

        assertThrows(IllegalArgumentException.class, () -> InconsistentZeroException.requireMatching(matrix1, matrix2));
    

        NavigableMap<Indexes, String> mapString = new TreeMap<>();
        mapString.put(new Indexes(0,0), "one");

        Matrix<Indexes, String> matrix3 = new NavigableMatrix<>(mapString, "zero");
        Matrix<Indexes, String> matrix4 = new NavigableMatrix<>(mapString, "0");

        assertThrows(IllegalArgumentException.class, () -> InconsistentZeroException.requireMatching(matrix3, matrix4));
        assertThrows(NullPointerException.class, () -> InconsistentZeroException.requireMatching(null, matrix4));
    }
}