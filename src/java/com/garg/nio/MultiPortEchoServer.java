package com.garg.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MultiPortEchoServer 
{
	public MultiPortEchoServer(final int[] ports) throws IOException 
	{
		configureSelector(ports);
	}

	private void configureSelector(final int[] ports) throws IOException 
	{
		Selector selector = Selector.open();
		ByteBuffer echoBuf = ByteBuffer.allocate(1024);
		
		for (int port : ports) {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket ss = ssc.socket();
			ss.bind(new InetSocketAddress(port));
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Listening on " + port);
		}
		
		while (true) {
			// Block until at 
			selector.select();
			
			Iterator<SelectionKey> itr = selector.selectedKeys().iterator();
			while (itr.hasNext()) {
				SelectionKey key = itr.next();
				if (key.isAcceptable()) {
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					SocketChannel sc = ssc.accept();
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
					System.out.println("Got connection from " + sc);
				}
				else if (key.isReadable()) {
					SocketChannel sc = (SocketChannel) key.channel();
					int bytesEchoed = 0;
					while (true) { 
						echoBuf.clear();
						int read = sc.read(echoBuf);
						if (read <= 0) {
							break;
						}
						echoBuf.flip();
						sc.write(ByteBuffer.wrap("EchoServer Response:\n".getBytes()));
						sc.write(echoBuf);
						bytesEchoed += read;
					}
					sc.close();
					System.out.println("Echoed " + bytesEchoed + " bytes from " + sc);
				}
				
				itr.remove();
			}
		}
	}

	public static void main(String[] args) throws IOException 
	{
		if (args.length <= 0) {
	        System.err.println("Usage: java MultiPortEchoServer port [port port ...]");
	        System.exit(1);
	      }
	  
	      int ports[] = new int[args.length];
	  
	      for (int i = 0; i < args.length; ++i) {
	        ports[i] = Integer.parseInt(args[i]);
	      }
	  
	      new MultiPortEchoServer(ports);	
	}
}
