/*
 * ClientSocketExample.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/server/ClientSocketExample.java $
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

package com.garg.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketExample
{
    public static void main(String[] args) throws ClassNotFoundException
    {
        try {
            // Create a connection to the server socket on the server application
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 7777);

            // Send a message to the client application
            ObjectOutputStream oos =
                new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("Hello There...");

            // Read and display the response message sent by server application
            ObjectInputStream ois =
                new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Message: " + message);

            ois.close();
            oos.close();
            socket.close();
            System.out.println("Done");
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
