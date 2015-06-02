/*
* SerializableTest.java
*
* $Header$
*/

/*
 * Copyright (c) 2008 D. E. Shaw & Co., L.P. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of D. E. Shaw & Co., L.P. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with D. E. Shaw & Co., L.P.
 */

package com.garg.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 *
 * @author Bhupinder Garg (gargb)
 * @version $Id: SerializableTest.java 4 2011-02-18 10:00:02Z bhupi.iit@gmail.com $
 */
public class SerializableTest
{
    public static void main(String... arguments)
    {
        List<String> symbols = Arrays.asList("IBM", "MSFT", "GOOG", "QQQQ");

        try {
            OutputStream file = new FileOutputStream("symbols.ser");
            GZIPOutputStream zip = new GZIPOutputStream(file);
            OutputStream buffer = new BufferedOutputStream(zip);
            ObjectOutput output = new ObjectOutputStream(buffer);

            try {
                output.writeObject(symbols);
            }
            finally {
                output.close();
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
        
        System.out.println("Written to symbols.ser");
        File filen = new File("symbols.ser");
        System.out.println("File size:" + filen.length());
        
        try {
            InputStream file = new FileInputStream("symbols.ser");
            GZIPInputStream zip = new GZIPInputStream(file);
            InputStream buffer = new BufferedInputStream(zip);
            ObjectInput input = new ObjectInputStream(buffer);
            try {
                Object readObject = input.readObject();
                System.out.println(readObject);
            } 
            finally {
                input.close();
            }
        }
        catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}
