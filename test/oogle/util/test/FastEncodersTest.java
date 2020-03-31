package oogle.util.test;

import oogle.util.byteable.FastDecoder;
import oogle.util.byteable.FastEncoder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class FastEncodersTest {
    @Test
    public void testByte(){
        for(int i = Byte.MIN_VALUE; i != Byte.MAX_VALUE + 1; i++){
            FastEncoder encoder = new FastEncoder();
            encoder.writeByte((byte)i);
            Assert.assertEquals(new FastDecoder(encoder.generate()).readByte(), (byte)i);
        }
    }

    @Test
    public void testShort(){
        for(int i = Short.MIN_VALUE; i != Short.MAX_VALUE + 1; i++){
            FastEncoder encoder = new FastEncoder();
            encoder.writeShort((short)i);
            Assert.assertEquals(new FastDecoder(encoder.generate()).readShort(), i);
        }
    }

    @Test
    public void testInt(){
        Random random = new Random();
        for(int i = 0; i < 1_000_000; i++){
            int rand = random.nextInt();
            FastEncoder encoder = new FastEncoder();
            encoder.writeInt(rand);
            Assert.assertEquals(new FastDecoder(encoder.generate()).readInt(), rand);
        }
    }

    @Test
    public void testLong(){
        Random random = new Random();
        for(int i = 0; i < 1_000_000; i++){
            long rand = random.nextLong();
            FastEncoder encoder = new FastEncoder();
            encoder.writeLong(rand);
            Assert.assertEquals(new FastDecoder(encoder.generate()).readLong(), rand);
        }
    }

    @Test
    public void testString(){
        Random random = new Random();
        for(int i = 0; i < 100_000; i++){
            char[] chars = new char[100];
            for(int y = 0; y < 100; y++)
                chars[y] = (char)random.nextInt(55000);//Java bug, on encode to UTF-8 char > 55000~ it convert to (char)63
            String str = new String(chars);
            try {
                FastEncoder encoder = new FastEncoder();
                encoder.writeString(str);
                Assert.assertEquals(new FastDecoder(encoder.generate()).readString(), str);
            }catch (Exception ex){
                System.out.println("Source string '" + str + "'");
                throw new RuntimeException(ex);
            }
        }
    }

    @Test
    public void testByteArray(){
        Random random = new Random();
        for(int i = 0; i < 100_000; i++){
            byte[] bytes = new byte[256];
            for(int y = 0; y < bytes.length; y++)
                bytes[y] = (byte)random.nextInt(256);
            FastEncoder encoder = new FastEncoder();
            encoder.writeBytes(bytes);
            Assert.assertArrayEquals(new FastDecoder(encoder.generate()).readBytes(), bytes);
        }
    }
}
