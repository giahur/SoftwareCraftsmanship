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
        return next.isPresent() || it.hasNext();
    }

    @Override
    public T next() {
        if(!hasNext()) {
            throw new NoSuchElementException("no next element");
        }
        T myNext = peek();
        next = Optional.empty();
        return myNext;
    }

    public T peek() {
        if(!next.isPresent() && it.hasNext()) {
            next = Optional.of(it.next());
        }
        return next.orElse(null);
    }

    public T element() {
        if(!hasNext()) {
            throw new NoSuchElementException("no next element");
        }
        if(!next.isPresent()) {
            next = Optional.of(it.next());
        }
        return next.get();
    }
}
