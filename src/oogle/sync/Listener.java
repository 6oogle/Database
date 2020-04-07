package oogle.sync;

public interface Listener {
    void read(Channel channel, byte[] array);

    void open(Channel channel);

    void close(Channel channel);

    default void tick(){}
}
