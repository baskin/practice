/*
 * UnixStatTest.java
 *
 * $Header: /proj/infra/repository/desmake/etc/deshaw_jalopy.xml,v 1.4 2007/09/12 10:24:53 lalam Exp $
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

package com.garg.scratchpad;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class UnixStatTest 
{
	private final PrintWriter myLogWtr;
	
	public UnixStatTest(String filePath) throws FileNotFoundException 
	{
		FileOutputStream file = new FileOutputStream(filePath, true);
		myLogWtr = new PrintWriter(file);
	}
	
	public void writeLog(String appendText)
	{
		myLogWtr.write(appendText);
		myLogWtr.flush();
	}
	
    /**
     * Close the writer stream.
     */
    public void close()
    {
        if (myLogWtr != null) {
            myLogWtr.close();
        }
    }
	
	public static void main(String[] args) 
	{
		try {
			UnixStatTest test = new UnixStatTest("tmpfile");
			String read = "y";
			int count = 0;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while(read.equals("y")) {
				test.writeLog("test" + count++);
				System.out.print("Press 'c' to append:");
				read = br.readLine();
			}
			br.close();
			test.close();
		} 
		catch (FileNotFoundException e) {
			System.err.println(e);
		}
		catch (IOException e) {
			System.err.println(e);
		}
	}
}
