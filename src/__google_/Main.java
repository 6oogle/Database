package __google_;

import __google_.util.Byteable;

public class Main{
    private int s = 123;
    private a a = new a();

    public static void main(String[] args){
        Main main2 = new Main();
        main2.a.a = 125;
        byte array[] = Byteable.toBytes(main2);
        Main main = Byteable.toObject(array, Main.class);
        System.out.println(main.a.a);
    }

    public static class a{
        int a = 10;
    }
}
