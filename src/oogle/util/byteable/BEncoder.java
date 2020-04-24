package oogle.util.byteable;

import oogle.util.annotation.NotNull;

public interface BEncoder extends Encoder {

    @NotNull
    byte[] generate();

    int generate(@NotNull byte[] array, int offset);
}
