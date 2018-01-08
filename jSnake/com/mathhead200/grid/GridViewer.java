package com.mathhead200.grid;

import java.io.*;

/**
 * used to view .jgs (Java Grid Source) files
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 */
public class GridViewer
{
	public static String ask(String str) throws IOException {
		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in) );
		System.out.print(str);
		return reader.readLine();
	}

	public static void main(String[] args) throws IOException
	{
		try {
			Grid.load( args.length > 0 ? args[0] : ask("Enter a filename: ") );
		} catch(IOException e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}
