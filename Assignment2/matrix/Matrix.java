package matrix;

import java.util.NavigableMap;
import java.util.function.BinaryOperator;
import java.util.Map;

public interface Matrix<I, T> {
    T value(I index);
    T zero();
    NavigableMap<I,T> representation();
    PeekingIterator<Map.Entry<I,T>> peekingIterator();
    public Matrix<I,T> merge(Matrix<I,T> other, BinaryOperator<T> op);
}
