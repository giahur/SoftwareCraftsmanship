package matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class TestPeekingIterator {

    @Test
    public void testPeekingIterator() {
        List<Integer> emptyList = new ArrayList<>();
        PeekingIterator<Integer> iteratorEmpty = PeekingIterator.from(emptyList.iterator());

        assertFalse(iteratorEmpty.hasNext());
        assertThrows(NoSuchElementException.class, () -> iteratorEmpty.next());
        assertEquals(null, iteratorEmpty.peek());

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        PeekingIterator<Integer> iterator = PeekingIterator.from(list.iterator());

        assertTrue(iterator.hasNext());
        assertEquals((Integer)1, iterator.peek());
        assertEquals((Integer)1, iterator.next());
        assertEquals((Integer)2, iterator.next());
        assertEquals((Integer)3, iterator.next());
        assertEquals((Integer)4, iterator.peek());
        assertEquals((Integer)4, iterator.next());
        assertEquals((Integer)5, iterator.next());
        assertFalse(iterator.hasNext());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(null, iterator.peek());
        assertThrows(NoSuchElementException.class, () -> iterator.element());

        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(null, iterator.peek());

        
    }
}
