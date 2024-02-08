package matrix;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public final class PeekingIterator<T> implements Iterator<T> {

    private final Iterator<T> it;
    private Optional<T> next;

    private PeekingIterator(Iterator<T> iterator) {
        this.it = iterator;
        this.next = Optional.empty();
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
        if(!hasNext()) {
            throw new NoSuchElementException("no next element");
        }
        return it.next();
    }

    public T peek() {
        T myNext = hasNext() ? next.get() : null;
        return myNext;
    }

    public T element() {
        if(!hasNext()) {
            throw new NoSuchElementException("no next element");
        }
        return next.get();
    }
}
