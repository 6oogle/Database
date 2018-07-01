package __google_.util;

public class Fast {
    public static String toLowerEN(String line){
        char result[] = new char[line.length()];
        char chars[] = line.toCharArray();
        for(int i = 0; i < chars.length; i++){
            char c = chars[i];
            if(c > 64 && c < 91) c = (char)(c + 32);
            result[i] = c;
        }
        return new String(result);
    }

    public static String toUpperEN(String line){
        char result[] = new char[line.length()];
        char chars[] = line.toCharArray();
        for(int i = 0; i < chars.length; i++){
            char c = chars[i];
            if(c > 96 && c < 123) c = (char)(c - 32);
            result[i] = c;
        }
        return new String(result);
    }
}
