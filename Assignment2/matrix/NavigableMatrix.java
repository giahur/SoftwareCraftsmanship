package matrix;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class NavigableMatrix<T> implements Matrix<Indexes, T> {
    
    private final NavigableMap<Indexes, T> matrix;
    private final T zero;
    
    private NavigableMatrix(NavigableMap<Indexes, T> matrix, T zero) {
        this.matrix = matrix;
        this.zero = zero;
    }

    @Override
    public T value(Indexes indexes) {
        Objects.requireNonNull(indexes);
        if(!matrix.containsKey(indexes)) {
            throw new IndexOutOfBoundsException("Indexes don't exist");
        }
        return matrix.get(indexes);
    }

    @Override
    public T zero() {
        return this.zero;
    }

    @Override
    public NavigableMap<Indexes, T> representation() {
        return Collections.unmodifiableNavigableMap(matrix);
    }

    @Override
    public PeekingIterator<Map.Entry<Indexes,T>> peekingIterator() {
        return PeekingIterator.from(matrix.entrySet().iterator());
    }

    public static class InvalidLengthException extends Exception {
        private static final long serialVersionUID = 221;

        public enum Cause {
            ROW, COLUMN
        }

        private Cause cause;
        private int length;

        public InvalidLengthException(Cause cause, int length) {
            this.cause = cause;
            this.length = length;
        }

        public Cause cause() {
            return this.cause;
        }

        public int length() {
            return this.length;
        }

        public int requireNonEmpty(Cause cause, int length) {
            Objects.requireNonNull(cause);
            if (length <= 0) {
                throw new IllegalArgumentException(new InvalidLengthException(cause, length));
            }
            return length;
        }
    }

    public static <S> NavigableMatrix<S> instance(int rows, int columns, Function<Indexes, S> valueMapper, S zero) {
        Objects.requireNonNull(valueMapper);
        Objects.requireNonNull(zero);

        if(rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("rows or columns cannot be less than 1");
        }
        Map<Indexes, S> myMap = Indexes.stream(rows, columns).collect(Collectors.toMap(x -> x, x -> valueMapper.apply((Indexes) x)));
        return new NavigableMatrix<S>(new TreeMap<Indexes, S>(myMap), zero);
    }

    public static <S> NavigableMatrix<S> constant(int rows, int columns, S value, S zero) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(zero);

        if(rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("rows or columns cannot be less than 1");
        }

        return instance(rows, columns, indexes -> value, zero);
    }

    public static <S> NavigableMatrix<S> identity(int size, S zero, S identity) {
        Objects.requireNonNull(zero);
        Objects.requireNonNull(identity);

        if(size < 1) {
            throw new IllegalArgumentException("size must be positive");
        }

        return instance(size, size, indexes -> indexes.areDiagonal() ? identity : zero, zero);
    }

    public static <S> NavigableMatrix<S> from(NavigableMap<Indexes, S> matrix, S zero) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(zero);

        return new NavigableMatrix<S>(matrix, zero);
    }
    public static <S> NavigableMatrix<S> from(S[][] matrix, S zero) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(zero);

        return instance(matrix.length, matrix[0].length, indexes -> indexes.value(matrix), zero);
    }
}