package oogle.crypt.sync;

import oogle.util.Converter;

public class Blowfish extends Crypt{
    public Blowfish(byte key[]){
        super("Blowfish", key);
    }

    public Blowfish(String key){
        this(key.getBytes());
    }
}