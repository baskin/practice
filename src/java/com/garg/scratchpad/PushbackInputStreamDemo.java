/*
 * PushbackInputStreamDemo.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/test/PushbackInputStreamDemo.java $
 */

/*
 * Copyright (c) 2009 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

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
