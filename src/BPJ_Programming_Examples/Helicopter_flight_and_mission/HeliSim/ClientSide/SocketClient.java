package BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim.ClientSide;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/*
 * SocketClient.
 * send out method,X;Y
 * 
 * 
 */

public class SocketClient {
	static String response;

	public SocketClient(String method, int x, int y) {

		/** Define a host server */
		String host = "127.0.0.1";
		/** Define a port */
		int port = 19999;

		StringBuffer instr = new StringBuffer();
		String TimeStamp;
		System.out.println("SocketClient initialized");
		try {
			/** Obtain an address object of the server */
			InetAddress address = InetAddress.getByName(host);
			/** Establish a socket connection */
			Socket connection = new Socket(address, port);
			/** Instantiate a BufferedOutputStream object */
			BufferedOutputStream bos = new BufferedOutputStream(
					connection.getOutputStream());

			/**
			 * Instantiate an OutputStreamWriter object with the optional
			 * character encoding.
			 */
			OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
			TimeStamp = new java.util.Date().toString();
			String process = method + ";" + x + ";" + y + (char) 13;

			/** Write across the socket connection and flush the buffer */
			osw.write(process);
			osw.flush();
			/**
			 * Instantiate a BufferedInputStream object for reading /**
			 * Instantiate a BufferedInputStream object for reading incoming
			 * socket streams.
			 */

			BufferedInputStream bis = new BufferedInputStream(
					connection.getInputStream());
			/**
			 * Instantiate an InputStreamReader with the optional character
			 * encoding.
			 */

			InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

			/** Read the socket's InputStream and append to a StringBuffer */
			int c;
			while ((c = isr.read()) != 13)
				instr.append((char) c);

			/** Close the socket connection. */
			connection.close();
			response = instr.toString();
			System.out.println(instr);
		} catch (IOException f) {
			System.out.println("IOException: " + f);
		} catch (Exception g) {
			System.out.println("Exception: " + g);
		}
	}

	public String getResponse() {
		return response;
	}

}