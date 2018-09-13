package __google_;

import __google_.util.Coder;

public class Main{
    private a b = a.h;

    public static void main(String[] args){
        Main main = new Main();
        main.b = a.b;
        Main main1 = Coder.toObject(Coder.toBytes(main), Main.class);
        System.out.println(main1.b);
    }

    public enum a{
        b,
        c,
        h
    }
}
