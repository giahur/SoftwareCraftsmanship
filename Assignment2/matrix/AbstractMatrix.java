package matrix;

import java.util.NavigableMap;
import java.util.Collections;
import java.util.Objects;

public abstract class AbstractMatrix<I, T> implements Matrix<I,T> {
    protected final NavigableMap<I,T> matrix;
    private final T zero;

    protected AbstractMatrix(NavigableMap<I, T> matrix, T zero) {
        this.matrix = matrix;
        //this.matrix = Collections.unmodifiableNavigableMap(matrix);
        this.zero = zero;
    }

    // returns element at given index, throws error if not found
    @Override
    public T value(I index) {
        Objects.requireNonNull(index);
        if(!matrix.containsKey(index)) {
            throw new IndexOutOfBoundsException("Index doesn't exist");
        }
        return matrix.get(index);
    }

    // represents value 0 for type T
    @Override
    public T zero() {
        return this.zero;
    }

    // returns copy of balanced binary tree representation of matrix
    @Override
    public NavigableMap<I,T> representation() {
        return Collections.unmodifiableNavigableMap(matrix);
    }
}