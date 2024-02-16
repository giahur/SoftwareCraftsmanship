package matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.Map;

import org.junit.Test;

public class TestMergeParameters {
    @Test
    public void testSetX() {
        Map.Entry<Integer, String> entry = Map.entry(1, "x");
        MergeParameters<Integer, String> mergeParams = new MergeParameters<>(null, null, null);

        MergeParameters<Integer, String> result = mergeParams.setX(entry);

        assertNotNull(result);
        assertEquals(entry.getValue(), result.x());
        assertEquals(null, result.y());
        assertThrows(NullPointerException.class, () -> mergeParams.setX(null));
    }

    @Test
    public void testSetY() {
        Map.Entry<Integer, String> entry = Map.entry(1, "y");
        MergeParameters<Integer, String> mergeParams = new MergeParameters<>(null, null, null);

        MergeParameters<Integer, String> result = mergeParams.setY(entry);

        assertNotNull(result);
        assertEquals(null, result.x());
        assertEquals(entry.getValue(), result.y());
        assertThrows(NullPointerException.class, () -> mergeParams.setY(null));
    }
}
