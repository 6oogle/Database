package __google_.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Coder {
    public static String eMap(Map<String, String> map){
        StringBuilder buffer = new StringBuilder();
        for(Map.Entry<String, String> entry : map.entrySet()){
            buffer.setLength(buffer.length() + entry.getKey().length() + entry.getValue().length() + 2);//'\n' and '=' size
            buffer.append(entry.getKey()).append('=').append(entry.getValue()).append('\n');
        }
        return buffer.toString();
    }

    public static Map<String, String> dMap(String str){
        String split[] = str.split("\n");
        Map<String, String> map = new HashMap<>(split.length);
        for(String line : split){
            String spl[] = line.split("=");
            if(spl.length != 2) continue;
            map.put(spl[0], spl[1]);
        }
        return map;
    }

    public static String eList(List<String> list){
        StringBuilder buffer = new StringBuilder();
        for(String line : list){
            buffer.setLength(buffer.length() + line.length() + 1);
            buffer.append(line).append('\n');
        }
        return buffer.toString();
    }

    public static List<String> dList(String str){
        String split[] = str.split("\n");
        List<String> list = new ArrayList<>(split.length);
        for(String line : split)
            list.add(line);
        return list;
    }
}
