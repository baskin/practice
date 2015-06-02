/*
 * ServerSocketExample.java
 *
 * $HeadURL: https://bhupi-practice.googlecode.com/svn/trunk/src/java/com/garg/server/ServerSocketExample.java $
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

package com.garg.concepts.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketExample
{
    static class ConnectionHandler implements Runnable
    {
        private Socket socket;

        public ConnectionHandler(ServerSocket server, Socket socket)
        {
            this.socket = socket;
        }

        public void run()
        {
            try {
                System.out.println("Connection handler:" + socket);

                // Read a message sent by client application
                ObjectInputStream ois =
                    new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("Message Received: " + message);

                // Send a response information to the client application
                ObjectOutputStream oos =
                    new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("Hi...");

                ois.close();
                oos.close();
                socket.close();
                System.out.println("Done with " + socket);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private ServerSocket server;
    private int port = 7777;

    public ServerSocketExample()
    {
        try {
            server = new ServerSocket(port);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConnection()
    {
        System.out.println("Waiting for client message...");

        //
        // The server do a loop here to accept all connection initiated by the
        // client application.
        //
        int num = 0;

        while (true) {
            try {
                Socket socket = server.accept();
                new Thread(new ConnectionHandler(server, socket),
                           "Server" + num++).start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        ServerSocketExample example = new ServerSocketExample();
        example.handleConnection();
    }
}
