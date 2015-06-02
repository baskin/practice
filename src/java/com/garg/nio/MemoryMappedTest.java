package com.garg.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

public class MemoryMappedTest {
	private static int FILE_SIZE = 20 * 1024 * 1024;

	public static void main(String[] args) throws IOException 
	{
		assert args != null;
		assert args.length == 1;

		String fn = args[0];
		long st = System.nanoTime();
		try (RandomAccessFile file = new RandomAccessFile(fn, "rw")) {

			MappedByteBuffer mappedBuf = file.getChannel().map(
					MapMode.READ_WRITE, 0, FILE_SIZE);

			for (int i = 0; i < FILE_SIZE; i++) {
				mappedBuf.put((byte) 'A');
			}
			System.out.println("File '" + fn + "' is now " + FILE_SIZE
					+ " bytes full.");

			// Read from memory-mapped file.
			for (int i = 0; i < 30; i++) {
				System.out.print((char) mappedBuf.get(i));
			}
			System.out.println("\nReading from memory-mapped file '" + fn
					+ "' is complete.");

		}
		System.out.println("Time taken to write 20Mb and read 30b: " + (System.nanoTime() - st)/1000 + " ms");

	}
}
