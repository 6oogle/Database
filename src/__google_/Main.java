package __google_;

import __google_.util.ByteUnzip;
import __google_.util.ByteZip;

public class Main{
    public static void main(String[] args){
        ByteZip zip = new ByteZip();
        zip.add("LolKek").add(123).add(12512552512512L);
        ByteUnzip unzip = new ByteUnzip(zip.build());
        System.out.println(unzip.getString());
        System.out.println(unzip.getInt());
        System.out.println(unzip.getLong());
    }
}
