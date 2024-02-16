package matrix;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.function.BinaryOperator;

import org.junit.Test;

public class TestMapMerger {
    @Test
    public void testMerge() {
        List<Map.Entry<Integer, String>> list1 = Arrays.asList(
            Map.entry(1, "A"),
            Map.entry(2, "B"),
            Map.entry(3, "C")
        );
        
        List<Map.Entry<Integer, String>> list2 = Arrays.asList(
            Map.entry(1, "X"),
            Map.entry(2, "Y"),
            Map.entry(4, "Z")
        );
        
        PeekingIterator<Map.Entry<Integer, String>> itThis = PeekingIterator.from(list1.iterator());
        PeekingIterator<Map.Entry<Integer, String>> itOther = PeekingIterator.from(list2.iterator());
        
        Comparator<Integer> comparator = Comparator.naturalOrder();
        BinaryOperator<String> op = (x, y) -> x + y;
        
        NavigableMap<Integer, String> result = MapMerger.merge(itThis, itOther, comparator, op, 0, "0");
        
        assertEquals("AX", result.get(1));
        assertEquals("BY", result.get(2));
        assertEquals("C0", result.get(3));
        assertEquals("0Z", result.get(4));
    }
}