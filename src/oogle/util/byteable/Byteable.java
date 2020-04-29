package oogle.util.byteable;

import oogle.util.annotation.NotNull;

public interface Byteable {

    void encode(@NotNull Encoder encoder);
}
