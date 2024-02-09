package matrix;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Objects;

public final class NavigableVector<T> extends AbstractMatrix<Integer, T> {
    protected NavigableVector(NavigableMap<Integer, T> matrix, T zero) {
        super(matrix, zero);
    }

    @Override
    public PeekingIterator<Map.Entry<Integer,T>> peekingIterator() {
        return PeekingIterator.from(matrix.entrySet().iterator());
    }

    // returns new NavigableVector initialized from unmodifiable copy of vector
    // in which any element that equals zero has been removed
    public static <S> NavigableVector<S> from(Map<Integer, S> vector, S zero) {
        Objects.requireNonNull(vector);
        Objects.requireNonNull(zero);
        Map<Integer, S> noZero = vector.entrySet()
                                        .stream()
                                        .filter(entry -> !(entry.getValue().equals(zero)))
                                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new NavigableVector<S>(new TreeMap<Integer, S>(noZero), zero);
    } 
}