package oogle.util;

import java.util.*;

public class Converter {
    public static boolean toBoolean(String line){
        return line != null && line.equals("true");
    }

    public static double toDouble(String line){
        return toDouble(line, -1);
    }

    public static double toDouble(String line, double error){
        if(line == null)return error;
        try{
            return Double.parseDouble(line);
        }catch (NumberFormatException ex){
            return error;
        }
    }

    public static int toInt(String line){
        return toInt(line, -1);
    }

    public static int toInt(String line, int error){
        if(line == null)return error;
        try{
            return Integer.decode(line);
        }catch (NumberFormatException ex){
            return error;
        }
    }

    public static Map<String, String> toMap(String str){
        if(str == null)return new HashMap<>();
        String split[] = str.split("\n");
        Map<String, String> map = new HashMap<>();
        for(String line : split){
            String spl[] = line.split("=", 2);
            if(spl.length == 1) map.put(spl[0], "");
            else map.put(spl[0], spl[1]);
        }
        return map;
    }

    public static String toString(Map<String, String> map){
        if(map == null)return "";
        StringBuilder buffer = new StringBuilder();
        for(Map.Entry<String, String> entry : map.entrySet())
            buffer.append(entry.getKey()).append('=').append(entry.getValue()).append('\n');
        return buffer.toString();
    }

    public static List<String> toList(String str){
        if(str == null)return new ArrayList<>();
        String split[] = str.split("\n");
        List<String> list = new ArrayList<>(split.length);
        Collections.addAll(list, split);
        return list;
    }

    public static String toString(List<String> list){
        if(list == null)return "";
        StringBuilder buffer = new StringBuilder();
        for(String line : list)
            buffer.append(line).append('\n');
        return buffer.toString();
    }
}
