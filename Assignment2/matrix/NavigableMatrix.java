package matrix;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class NavigableMatrix<T> implements Matrix<Indexes, T> {
    
    private final T zero;
    private final NavigableMap<Indexes, T> matrixByRows;
    private final NavigableMap<Indexes, T> matrixByColumns;
    
    protected NavigableMatrix(T zero, NavigableMap<Indexes, T> matrixByRows, NavigableMap<Indexes, T> matrixByColumns) {
        this.zero = zero;
        this.matrixByRows = Collections.unmodifiableNavigableMap(matrixByRows);
        this.matrixByColumns = Collections.unmodifiableNavigableMap(matrixByColumns);
    }

    @Override
    public T value(Indexes indexes) {
        return matrixByRows.getOrDefault(indexes, zero());
    }

    @Override
    public T zero() {
        return this.zero;
    }

    @Override
    public NavigableMap<Indexes, T> representation() {
        return Collections.unmodifiableNavigableMap(matrixByRows); //change to matrixByColumns/Rows?
    }

    @Override
    public PeekingIterator<Map.Entry<Indexes, T>> peekingIterator() {
        return PeekingIterator.from(matrixByRows.entrySet());
    }

    @Override
    public Matrix<Indexes, T> merge(Matrix<Indexes, T> other, BinaryOperator<T> op) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(op);
        T newZero = InconsistentZeroException.requireMatching(this, other);
        NavigableMap<Indexes, T> map = MapMerger.merge(this.peekingIterator(), other.peekingIterator(), Comparator.naturalOrder(), op, Indexes.ORIGIN, newZero);
        return new NavigableMatrix<T>(zero, map, matrixByCol(map));
    }

    public static class InvalidLengthException extends Exception {
        private static final long serialVersionUID = 123;

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

        public static int requireNonEmpty(Cause cause, int length) {
            Objects.requireNonNull(cause);
            if (length <= 0) {
                throw new IllegalArgumentException(new InvalidLengthException(cause, length));
            }
            return length;
        }
    }

    private static <S> TreeMap<Indexes, S> matrixByCol(Map<Indexes, S> map) {
        assert map != null;
        TreeMap<Indexes, S> newColMap = new TreeMap<Indexes, S>(Indexes.byColumns);
        newColMap.putAll(map);
        return newColMap;
    }

    // returns entries where row = i, keys are columns
    public NavigableVector<T> row(int i) {
        return rowColumn(matrixByRows, entry -> entry.getKey().row() == i, entry -> entry.getKey().column());
    }

    public NavigableVector<T> column(int j) {
        return rowColumn(matrixByColumns, entry -> entry.getKey().column() == j, entry -> entry.getKey().row());
    }


    public NavigableVector<T> rowColumn(NavigableMap<Indexes, T> matrixBy, Predicate<Entry<Indexes, T>> predicateFilter, Function<Entry<Indexes, T>, Integer> functionCollect) {
        Objects.requireNonNull(matrixBy);
        Objects.requireNonNull(predicateFilter);
        Objects.requireNonNull(functionCollect); 
        return NavigableVector.from(matrixBy.entrySet()
                                                .stream()
                                                .filter(predicateFilter)
                                                .collect(Collectors.toMap(functionCollect, Entry::getValue, (a,b) -> a, TreeMap::new)), zero());
    } 

    public static <S> NavigableMatrix<S> instance(int rows, int columns, Function<Indexes, S> valueMapper, S zero) {
        Objects.requireNonNull(valueMapper);
        Objects.requireNonNull(zero);

        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);

        Map<Indexes, S> myMap = Indexes.stream(rows, columns)
                .filter(x -> !valueMapper.apply(x).equals(zero))
                .collect(Collectors.toMap(x -> x, x -> valueMapper.apply((Indexes) x)));
        return new NavigableMatrix<S>(zero, new TreeMap<Indexes, S>(myMap), matrixByCol(myMap));
    }

    public static <S> NavigableMatrix<S> constant(int rows, int columns, S value, S zero) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(zero);

        return instance(rows, columns, indexes -> value, zero);
    }

    public static <S> NavigableMatrix<S> identity(int size, S zero, S identity) {
        Objects.requireNonNull(zero);
        Objects.requireNonNull(identity);

        return instance(size, size, indexes -> indexes.areDiagonal() ? identity : zero, zero);
    }

    public static <S> NavigableMatrix<S> from(NavigableMap<Indexes, S> matrix, S zero) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(zero);

        return new NavigableMatrix<S>(zero, matrix, matrixByCol(matrix));
    }
    
    public static <S> NavigableMatrix<S> from(S[][] matrix, S zero) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(zero);

        return instance(matrix.length, matrix[0].length, indexes -> indexes.value(matrix), zero);
    }
}