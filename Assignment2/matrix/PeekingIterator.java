package matrix;

import java.util.Iterator;
import java.util.Optional;

public final class PeekingIterator<T> implements Iterator<T> {

    private final Iterator<T> it;
    private Optional<T> next;

    private PeekingIterator(Iterator<T> iterator) {
        this.it = iterator;
        this.next = Optional.of(iterator.next());
    }

    public static <T> PeekingIterator<T> from(Iterator<T> iterator) {
        return new PeekingIterator<>(iterator);
    }

    public static <T> PeekingIterator<T> from(Iterable<T> iterable) {
        return new PeekingIterator<>(iterable.iterator());
    }

    @Override
    public boolean hasNext() {
        return next.isPresent();
    }

    @Override
    public T next() {
        T value = next.get();
        if(it.hasNext()) {
            next = Optional.of(it.next());
        }
        else {
            next = Optional.empty();
        }
        return value;
    }

    public Optional<T> peek() {
        if(!hasNext()) {
            return Optional.empty();
        }
        return next;
    }

    public T element() {
        return next.get();
    }
}
