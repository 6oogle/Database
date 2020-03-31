package oogle.util.byteable;

import java.util.Arrays;

final class ByteList {
    byte[] elementData;
    int size;

    public ByteList(int initialCapacity) {
        elementData = new byte[initialCapacity];
    }

    public ByteList() {
        this(10);
    }

    private void grow() {
        elementData = Arrays.copyOf(elementData, this.size * 2);
    }

    private void grow(int need) {
        int newCapacity;
        if (need <= this.size) newCapacity = this.size * 2;
        else newCapacity = need + size;
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    void add(byte e) {
        if (size == elementData.length) this.grow();
        elementData[size++] = e;
    }

    void add(byte[] array) {
        int need = size + array.length;
        if (need >= elementData.length) grow(need);
        System.arraycopy(array, 0, elementData, size, array.length);
        this.size = need;
    }

    void add(byte[] array, int offset, int inputArraySize){
        int need = size + inputArraySize;
        if (need >= elementData.length) grow(need);
        System.arraycopy(array, offset, elementData, size, inputArraySize);
        this.size = need;
    }
}