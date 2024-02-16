package matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;

public class TestPeekingIterator {

    @Test
    public void testPeekingIterator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        PeekingIterator<Integer> iterator = PeekingIterator.from(list.iterator());

        assertTrue(iterator.hasNext());
        assertEquals(Optional.of(1), iterator.peek());
        assertEquals((Integer)1, iterator.next());
        assertEquals((Integer)2, iterator.next());
        assertEquals((Integer)3, iterator.next());
        assertEquals(Optional.of(4), iterator.peek());
        assertEquals((Integer)4, iterator.next());
        assertEquals((Integer)5, iterator.next());
        assertFalse(iterator.hasNext());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(Optional.empty(), iterator.peek());
        assertThrows(NoSuchElementException.class, () -> iterator.element());

        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertEquals(Optional.empty(), iterator.peek());   
    }
}
