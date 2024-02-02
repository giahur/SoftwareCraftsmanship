package matrix;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Objects;

public final class NavigableVector<T> extends AbstractMatrix<Integer, T> {
    private NavigableVector(NavigableMap<Integer, T> vector, T zero) {
        super(vector, zero);
    }

    // returns new NavigableVector initialized from unmodifiable copy of vector
    // in which any element that equals zero has been removed
    public static <S> NavigableVector<S> from(Map<Integer, S> vector, S zero) {
        Objects.requireNonNull(vector);
        Objects.requireNonNull(zero);
        NavigableMap<Integer, S> noZero = new TreeMap<>();
        for(Map.Entry<Integer, S> entry : vector.entrySet()) {
            if(!entry.getValue().equals(zero)) {
                noZero.put(entry.getKey(), entry.getValue());
            }
        }
        return new NavigableVector<>(noZero, zero);

    }
}