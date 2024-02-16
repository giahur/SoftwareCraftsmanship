package matrix;

import java.util.Comparator;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;

//protected
final class MapMerger {

    public static <K, V> NavigableMap<K, V> merge(
            PeekingIterator<Entry<K, V>> itThis,
            PeekingIterator<Entry<K, V>> itOther,
            Comparator<? super K> comparator,
            BinaryOperator<V> op,
            K origin,
            V zero) {
        Objects.requireNonNull(itThis);
        Objects.requireNonNull(itOther);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(op);
        Objects.requireNonNull(origin);
        Objects.requireNonNull(zero);

        NavigableMap<K, V> map = new TreeMap<>();

        while (itThis.hasNext() || itOther.hasNext()) {
            MergeParameters<K, V> mergeParams = mergeParameters(itThis, itOther, comparator, op, origin, zero);
            map.put(mergeParams.index(), op.apply(mergeParams.x(), mergeParams.y()));
        }
        return map;
    }

    private static <K, V> MergeParameters<K, V> mergeParameters(
            PeekingIterator<Entry<K, V>> itThis,
            PeekingIterator<Entry<K, V>> itOther,
            Comparator<? super K> comparator,
            BinaryOperator<V> op,
            K origin,
            V zero) {
        Objects.requireNonNull(itThis);
        Objects.requireNonNull(itOther);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(op);
        Objects.requireNonNull(origin);
        Objects.requireNonNull(zero);

        MergeParameters<K,V> mergeParams = new MergeParameters<K,V>(origin, zero, zero);

        if (itThis.hasNext() && itOther.hasNext()) {
            mergeParams = stepParameters(itThis, itOther, comparator, mergeParams);
        } 
        else if (itThis.hasNext()) {
            mergeParams = stepParameters(itThis, mergeParams::setX);
        } 
        else if (itOther.hasNext()) {
            mergeParams = stepParameters(itOther, mergeParams::setY);
        }
        return mergeParams;
    }

    private static <K, V> MergeParameters<K, V> stepParameters(
            PeekingIterator<Entry<K, V>> itThis,
            PeekingIterator<Entry<K, V>> itOther,
            Comparator<? super K> comparator,
            MergeParameters<K, V> mergeParameters) {
        Objects.requireNonNull(itThis);
        Objects.requireNonNull(itOther);
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(mergeParameters);

        MergeParameters<K,V> mp = mergeParameters;
        int compare = comparator.compare(itThis.element().getKey(), itOther.element().getKey());
        if(compare <= 0) {
            mp = stepParameters(itThis, mergeParameters::setX);
        }
        if(compare >= 0) {
            mp = stepParameters(itOther, mp::setY);
        }
        return mp;
    }

    private static <K, V> MergeParameters<K, V> stepParameters(
            PeekingIterator<Entry<K, V>> iterator,
            Function<Entry<K, V>, MergeParameters<K,V>> parameters
            ) {
        Objects.requireNonNull(iterator);
        Objects.requireNonNull(parameters);

        return parameters.apply(iterator.next());
    }
}