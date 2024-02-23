package matrix;

import java.util.Map;
import java.util.Objects;

record MergeParameters<K, V>(K index, V x, V y) {
    MergeParameters<K, V> setX(Map.Entry<K, V> contents) {
        Objects.requireNonNull(contents);
        return new MergeParameters<>(contents.getKey(), contents.getValue(), this.y());
    }

    MergeParameters<K, V> setY(Map.Entry<K, V> contents) {
        Objects.requireNonNull(contents);
        return new MergeParameters<>(contents.getKey(), this.x(), contents.getValue());
    }
}
