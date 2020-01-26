package oogle.util.byteable;

import java.util.*;

class ByteList {
    byte[] elementData;
    int size;

    public ByteList(int initialCapacity) {
        elementData = new byte[initialCapacity];
    }

    public ByteList(){
        this(10);
    }

    private void grow() {
        elementData = Arrays.copyOf(elementData, this.size * 2);
    }

    public void add(byte e) {
        if (size == elementData.length) this.grow();
        elementData[size++] = e;
    }
}