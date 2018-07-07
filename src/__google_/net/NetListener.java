package __google_.net;

import __google_.util.Listener;

public interface NetListener extends Listener<String>{
    default void onConnected(CSSystem system){}
}
