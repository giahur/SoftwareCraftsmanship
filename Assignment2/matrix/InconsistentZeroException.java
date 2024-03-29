package matrix;

import java.util.Objects;

public final class InconsistentZeroException extends Exception {
    private static final long serialVersionUID = 456;
    private final String thisZero;
    private final String otherZero;

    public InconsistentZeroException(String thisZero, String otherZero) {
        super("Inconsistent zeros");
        this.thisZero = thisZero;
        this.otherZero = otherZero;
    }

    public String thisZero() {
        return thisZero;
    }

    public String otherZero() {
        return otherZero;
    }

    public static <I, T> T requireMatching(Matrix<I, T> thisMatrix, Matrix<I, T> otherMatrix) {
        Objects.requireNonNull(thisMatrix);
        Objects.requireNonNull(otherMatrix);
        
        if (!thisMatrix.zero().equals(otherMatrix.zero())) {
            throw new IllegalArgumentException(new InconsistentZeroException(thisMatrix.zero().toString(), otherMatrix.zero().toString()));
        }

        return thisMatrix.zero();
    }
}