package matrix;

import static org.junit.Assert.assertThrows;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.Test;

public class TestInconsistentZeroException {
    public static NavigableMatrix<Integer> createMatrix(Integer val, Integer zero) {
        NavigableMatrix<Integer> myNewMatrix = NavigableMatrix.instance(2, 2, (index) -> val, zero);
        return myNewMatrix;
    }

    public static NavigableMatrix<String> createMatrix(String val, String zero) {
        NavigableMatrix<String> myNewMatrix = NavigableMatrix.instance(2, 2, (index) -> val, zero);
        return myNewMatrix;
    }

    @Test
    public void testrequireMatching() {

        NavigableMap<Indexes, Integer> mapInt = new TreeMap<>();
        mapInt.put(new Indexes(0,0), 1);

        Matrix<Indexes, Integer> matrix1 = createMatrix(3, 2);
        Matrix<Indexes, Integer> matrix2 = createMatrix(6, 3);

        assertThrows(IllegalArgumentException.class, () -> InconsistentZeroException.requireMatching(matrix1, matrix2));
    
        Matrix<Indexes, String> matrix3 = createMatrix("one", "zero");
        Matrix<Indexes, String> matrix4 = createMatrix("five", "0");

        assertThrows(IllegalArgumentException.class, () -> InconsistentZeroException.requireMatching(matrix3, matrix4));
        assertThrows(NullPointerException.class, () -> InconsistentZeroException.requireMatching(null, matrix4));
    }
}