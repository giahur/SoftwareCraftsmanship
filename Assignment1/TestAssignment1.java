import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.*;

public class TestAssignment1 {
    Assignment1<Integer> assignment = new Assignment1<>();
    Comparator<Integer> cmp = new Comparator<Integer>() {
        @Override
        public int compare(Integer x, Integer y) {
            return x.compareTo(y);
        }
    };

    @Test
    public void testLongestHigherSuffix1() {
        // Test Case 1
        List<Integer> a1 = new ArrayList<>();
        Collections.addAll(a1, 0, 2, 4);
        List<Integer> b1 = new ArrayList<>();
        Collections.addAll(b1, 1, 2, 3);
        List<Integer> expected1 = new ArrayList<>();
        Collections.addAll(expected1, 2, 4);
        assertEquals(assignment.longestHigherSuffix(a1, b1, cmp), expected1);
    }

    @Test
    public void testLongestHigherSuffix2() {
        // Test Case 2
        List<Integer> a2 = new ArrayList<>();
        Collections.addAll(a2, 1, 2);
        List<Integer> b2 = new ArrayList<>();
        Collections.addAll(b2, 2, 1);
        List<Integer> expected2 = new ArrayList<>();
        Collections.addAll(expected2, 2);
        
        assertEquals(assignment.longestHigherSuffix(a2, b2, cmp), expected2);
    }

    @Test
    public void testLongestHigherSuffix3() {
        // Test Case 3
        List<Integer> a3 = new ArrayList<>();
        Collections.addAll(a3, 2, 4);
        List<Integer> b3 = new ArrayList<>();
        Collections.addAll(b3, 1, 3, 2, 4);
        List<Integer> expected3 = new ArrayList<>();
        Collections.addAll(expected3, 2, 4);
        
        assertEquals(assignment.longestHigherSuffix(a3, b3, cmp), expected3);
    }

    @Test
    public void testLongestHigherSuffix4() {
        // Test Case 4
        List<Integer> a4 = new ArrayList<>();
        Collections.addAll(a4, 1, 2, 3, 4);
        List<Integer> b4 = new ArrayList<>();
        Collections.addAll(b4, 1, 2, 4);
        List<Integer> expected4 = new ArrayList<>();
        Collections.addAll(expected4, 2, 3, 4);
        
        assertEquals(assignment.longestHigherSuffix(a4, b4, cmp), expected4);
    }

    @Test
    public void testLongestHigherSuffix5() {
        // Test Case 5
        List<Integer> a5 = new ArrayList<>();
        Collections.addAll(a5, 2, 1);
        List<Integer> b5 = new ArrayList<>();
        Collections.addAll(b5, 1, 2, 3);
        List<Integer> expected5 = new ArrayList<>();
        
        assertEquals(assignment.longestHigherSuffix(a5, b5, cmp), expected5);
    }

    @Test
    public void testLongestHigherSuffix6() {
        // Test Case 6
        List<Integer> a6 = new ArrayList<>();
        Collections.addAll(a6, 1, 3, 2, 4);
        List<Integer> b6 = new ArrayList<>();
        Collections.addAll(b6, 1, 2, 3, 4);
        List<Integer> expected6 = new ArrayList<>();
        Collections.addAll(expected6, 4);
        
        assertEquals(assignment.longestHigherSuffix(a6, b6, cmp), expected6);
    }
}
