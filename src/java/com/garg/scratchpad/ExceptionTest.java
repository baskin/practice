
package com.garg.scratchpad;

import java.io.IOException;

public class ExceptionTest
{
    private void foo1() throws IOException
    {
        throw new IOException("Io error");
    }

    private void foo2() throws IOException
    {
        foo1();
    }

    private void foo3() throws IOException
    {
    	try {
    		foo2();
    	}
    	catch (IOException e) {
//    		throw e;
    		throw new IOException("Again Io ", e);
    	}
    }

    private void foo4() throws IOException
    {
        foo3();
    }
    
    private void foo5() throws IOException 
    {
    	foo4();
	}

    public static void main(String[] args)
    {
        ExceptionTest test = new ExceptionTest();

        try {
            test.foo5();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
