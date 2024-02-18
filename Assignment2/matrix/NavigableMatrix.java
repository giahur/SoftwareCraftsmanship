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
import java.util.stream.Collectors;

public final class NavigableMatrix<T> implements Matrix<Indexes, T> {
    
    //private final NavigableMap<Indexes, T> matrix; delete??
    private final T zero;
    private final NavigableMap<Indexes, T> matrixByColumns;
    private final NavigableMap<Indexes, T> matrixByRows;
    
    protected NavigableMatrix(NavigableMap<Indexes, T> matrixByRows, T zero) {
        this.matrixByRows = Collections.unmodifiableNavigableMap(matrixByRows);
        this.zero = zero;

        //CHANGE THIS... maybe helper method w argument matrixByRows
        this.matrixByColumns = Collections.unmodifiableNavigableMap(matrixByRows);
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
        return Collections.unmodifiableNavigableMap(matrix); //change to matrixByColumns/Rows?
    }

    @Override
    public PeekingIterator<Map.Entry<Indexes,T>> peekingIterator() {
        return PeekingIterator.from(matrix.entrySet());
    }

    @Override
    public Matrix<Indexes, T> merge(Matrix<Indexes, T> other, BinaryOperator<T> op) {
        NavigableMap<Indexes, T> map = MapMerger.merge(this.peekingIterator(), other.peekingIterator(), Comparator.naturalOrder(), op, Indexes.ORIGIN, zero());
        InconsistentZeroException.requireMatching(this, other);
        return new NavigableMatrix<T>(map, zero);
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

    // returns entries where row = i, keys are columns
    public NavigableVector<T> row(int i) {
        NavigableMap<Integer, T> rowMap = matrixByColumns.entrySet()
                                                .stream()
                                                .filter(entry -> entry.getKey().row() == i)
                                                .collect(Collectors.toMap(entry -> entry.getKey().column(), Entry::getValue, (a,b) -> a, TreeMap::new));
        return new NavigableVector<T>(rowMap, zero);
    }

    public NavigableVector<T> column(int j) {
        NavigableMap<Integer, T> columnMap = matrixByRows.entrySet()
                                                .stream()
                                                .filter(entry -> entry.getKey().column() == j)
                                                .collect(Collectors.toMap(entry -> entry.getKey().row(), Entry::getValue, (a,b) -> a, TreeMap::new));
        return new NavigableVector<T>(columnMap, zero);
    }

    //repeated code??
    /*public NavigableVector<T> rowColumn(NavigableMap<Indexes, T> matrixBy, int rowColumn, int ij, int columnRow) {
        NavigableMap<Integer, T> rowColumnMap = matrixBy.entrySet()
                                                .stream()
                                                .filter(entry -> entry.getKey().rowColumn == ij)
                                                .collect(Collectors.toMap(entry -> entry.getKey().columnRow, Entry::getValue, (a,b) -> a, TreeMap::new));
        return new NavigableVector<T>(rowColumnMap, zero);
    } */

    public static <S> NavigableMatrix<S> instance(int rows, int columns, Function<Indexes, S> valueMapper, S zero) {
        Objects.requireNonNull(valueMapper);
        Objects.requireNonNull(zero);

        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);

        Map<Indexes, S> myMap = Indexes.stream(rows, columns)
                .filter(x -> !valueMapper.apply(x).equals(zero))
                .collect(Collectors.toMap(x -> x, x -> valueMapper.apply((Indexes) x)));
        return new NavigableMatrix<S>(new TreeMap<Indexes, S>(myMap), zero);
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

        return new NavigableMatrix<S>(matrix, zero);
    }
    
    public static <S> NavigableMatrix<S> from(S[][] matrix, S zero) {
        Objects.requireNonNull(matrix);
        Objects.requireNonNull(zero);

        return instance(matrix.length, matrix[0].length, indexes -> indexes.value(matrix), zero);
    }
}