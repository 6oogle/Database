package __google_;

import __google_.io.FileIO;
import __google_.util.Coder;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("lolkek", "mmm");
        map.put("asffasfsa", "afsfаапфыпas");
        System.out.println(Coder.eMap(map));
        FileIO.write("Lol", Coder.eMap(map));
    }
}
