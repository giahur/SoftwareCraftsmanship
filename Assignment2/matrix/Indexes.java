package matrix;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public record Indexes(int row, int column) implements Comparable<Indexes> {
    public static final Indexes ORIGIN = new Indexes (0,0);

    @Override
    public int compareTo(Indexes o) {
        Objects.requireNonNull(o);
        return byRows.compare(this, o);
    }

    // return new Indexes with given row value
    public Indexes withRow(int row) {
        return new Indexes(row, this.column());
    }
    
    // return new Indexes with given column value
    public Indexes withColumn(int column) {
        return new Indexes(this.row(), column);
    }

    //first compares Indexes by row and breaks any tie by column
    public static final Comparator<Indexes> byRows = Comparator.comparingInt(Indexes::row)
            .thenComparingInt(Indexes::column);

    //first compares Indexes by column and breaks any tie by row
    public static final Comparator<Indexes> byColumns = Comparator.comparingInt(Indexes::column)
            .thenComparingInt(Indexes::row);
        
    public <S> S value(S[][] matrix) {
        Objects.requireNonNull(matrix);
        return matrix[this.row()][this.column()];
    }

    public <S> S value(Matrix<Indexes, S> matrix) {
        return matrix.value(this);
    }

    public boolean areDiagonal() {
        return (this.row() == this.column());
    }

    // generates all indexes between from - to
    public static Stream<Indexes> stream(Indexes from, Indexes to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        return IntStream.range(from.row(), to.row())
                    .mapToObj(x -> IntStream.range(from.column(), to.column())
                    .mapToObj(y -> new Indexes(x, y))).flatMap(Function.identity());
    }
    

    public static Stream<Indexes> stream(Indexes size) {
        Objects.requireNonNull(size);
        return stream(ORIGIN, size);
    }

    public static Stream<Indexes> stream(int rows, int columns) {
        return stream(ORIGIN, new Indexes(rows, columns));
    }
}