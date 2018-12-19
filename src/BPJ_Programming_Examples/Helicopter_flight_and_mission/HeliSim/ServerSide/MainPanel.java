package BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim.ServerSide;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainPanel implements Runnable {

	private Socket connection;
	private String TimeStamp;
	private int ID;
	static int f;
	static Frame frame = new Frame("Helicopter simulator V1.0");;
	static final int FRAME_SIZE = 500;
	static final int HELICOPTER_PAINT_SIZE = 10;
	static final int port = 19999;

	public static void main(String[] args) {
		MainPanel.MainWin();
	}

	public static void MainWin() {

		int count = 0;

		try {
			ServerSocket socket1 = new ServerSocket(port);
			System.out.println("MultipleSocketServer Initialized");
			// FRAME

			while (true) {
				Socket connection = socket1.accept();
				Runnable runnable = new MainPanel(connection, ++count);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (Exception e) {
		}
	}

	MainPanel(Socket s, int i) {
		this.connection = s;
		this.ID = i;
	}

	public void run() {
		try {
			BufferedInputStream is = new BufferedInputStream(
					connection.getInputStream());
			InputStreamReader isr = new InputStreamReader(is);
			int character;
			StringBuffer process = new StringBuffer();
			while ((character = isr.read()) != 13) {
				process.append((char) character);
			}

			f++;
			try {

			} catch (Exception e) {
			}
			System.out.println(process.toString());

			TimeStamp = new java.util.Date().toString();
			String returnCode = "MultipleSocketServer repsonded at "
					+ TimeStamp + " counts:" + f + (char) 13;

			String[] ProcessXY = process.toString().split(";");

			if (ProcessXY[0].equals("Fixed")) {
				System.out.println("got it");
				frame.MoveHeli(Integer.parseInt(ProcessXY[1]),
						Integer.parseInt(ProcessXY[2]), ProcessXY[3]);
				System.out.println(ProcessXY[0] + ";" + ProcessXY[1] + ";"
						+ ProcessXY[2] + ";" + ProcessXY[3]);
			} else if (ProcessXY[0].equals("Rel")) {

				frame.MoveHeliRel(Integer.parseInt(ProcessXY[1]),
						Integer.parseInt(ProcessXY[2]), ProcessXY[3]);
				System.out.println(ProcessXY[0] + ";" + ProcessXY[1] + ";"
						+ ProcessXY[2] + ";" + ProcessXY[3]);

			} else if (ProcessXY[0].equals("CLEAR_SIM")) {
				frame.eraseBoard();
			}

			BufferedOutputStream os = new BufferedOutputStream(
					connection.getOutputStream());
			OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");

			osw.write(returnCode);
			osw.flush();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
			}
		}
	}
}