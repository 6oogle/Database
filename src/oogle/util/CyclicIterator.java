package oogle.util;

import java.util.Iterator;

public final class CyclicIterator<T> implements Iterator<T> {
    private final T[] array;
    private int pos = 0;

    @SafeVarargs
    public CyclicIterator(T... array) {
        if(array == null) throw new IllegalArgumentException("Input array is null");
        if(array.length == 0)throw new IllegalArgumentException("Input array is empty");
        this.array = array;
    }

    @Override
    public T next() {
        if (array.length == pos) pos = 0;
        return array[pos++];
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
