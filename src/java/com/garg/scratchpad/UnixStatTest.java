
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
