
package com.garg.scratchpad;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

/**
 * Demo for ByteArrayInputStream.
 * 
 * <p>Pushback is used on an input stream to allow a byte to be read and then 
 * returned (that is, "pushed back") to the stream. The PushbackInputStream 
 * class implements this idea. It provides a mechanism to "peek" at what is 
 * coming from an input stream without disrupting it.
 *
 * @author gargb
 */
public class PushbackInputStreamDemo
{
    public static void main(String[] args) throws IOException
    {
        String s = "if (a == 4) a = 0;\\n";
        byte[] buf = s.getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        PushbackInputStream f = new PushbackInputStream(in);
        
        System.out.print((char) f.read());
        System.out.print((char) f.read());
        System.out.print((char) f.read());
        System.out.print((char) f.read());
        
        f.unread('c');
        System.out.print((char)f.read());
        
        System.out.print((char) f.read());
        System.out.print((char) f.read());
        System.out.print((char) f.read());
        System.out.print((char) f.read());
    }
}
