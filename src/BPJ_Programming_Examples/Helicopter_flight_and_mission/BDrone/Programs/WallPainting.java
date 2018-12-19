package BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.Programs;

import static bp.BProgram.bp;
import bApplication.BApplication;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.Colors.ChangePaintingColor;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.Colors.ColorControl;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols.DoColD2U;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols.DoColU2D;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols.DoRowL2R;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols.EndColDown;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.RowsAndCols.EndColUp;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.StartAndFinish.BeginPaintingTheWall;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.StartAndFinish.IdlingThread;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.StartAndFinish.StopPaintingTheWall;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.environment.Wind;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.environment.XAxis;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.environment.YAxis;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.BrushMove;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.FixWind;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingDown;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingDownMaint;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingDownMi;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingLeft;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingLeftMaint;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingLeftMi;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingRight;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingRightMaint;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingRightMi;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingUp;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingUpMaint;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.bThreads.moves.MovingUpMi;
import bp.BProgram;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.Colors.ChangeColor;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.DoColDown;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.DoColUp;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.DoRowLeft;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.DoRowRight;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.EndOfColDown;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.EndOfColUp;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.EndOfRowLeft;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.ColsAndRows.EndOfRowRight;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.GPS.updateArrived;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.Moves.MoveDown;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.Moves.MoveLeft;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.Moves.MoveRight;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.Moves.MoveUp;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.StartAndFinish.BeginPainting;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.StartAndFinish.FinishPainting;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.util.Idle;
import BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone.events.util.Tick;

public class WallPainting implements BApplication {

	/*
	 * WallPainting - created by Ido Rosenbaum, 23/12/2013
	 * 
	 * Thanks to: Dr. Assaf Maron, Professor David Harel, Amir Sagie, Smadar
	 * Szekely . Weizmann Institute.
	 */

	// TODO PARAMETERS

	static SocketClient a = new SocketClient();

	public static drone BDrone = new drone();

	public static Tick tick = new Tick("ClockTick");
	public static Idle idle = new Idle("idle");

	public static MoveRight eMoveRight = new MoveRight("MoveRight");
	public static MoveUp eMoveUp = new MoveUp("MoveUp");
	public static MoveDown eMoveDown = new MoveDown("MoveDown");
	public static MoveLeft eMoveLeft = new MoveLeft("MoveLeft");

	public static MoveRight eMoveRightMi = new MoveRight("MoveRightMi");
	public static MoveUp eMoveUpMi = new MoveUp("MoveUpMi");
	public static MoveDown eMoveDownMi = new MoveDown("MoveDownMi");
	public static MoveLeft eMoveLeftMi = new MoveLeft("MoveLeftMi");

	public static MoveRight eMoveRightMaint = new MoveRight("MoveRightMaint",
			true);
	public static MoveUp eMoveUpMaint = new MoveUp("MoveUpMaint", true);
	public static MoveDown eMoveDownMaint = new MoveDown("MoveDownMaint", true);
	public static MoveLeft eMoveLeftMaint = new MoveLeft("MoveLeftMaint", true);

	public static EndOfColUp eEndOfColUp = new EndOfColUp("EndOfColUp");
	public static EndOfColDown eEndOfColDown = new EndOfColDown("EndOfColDown");
	public static EndOfRowRight eEndOfRowRight = new EndOfRowRight(
			"EndOfRowRight");
	public static EndOfRowLeft eEndOfRowLeft = new EndOfRowLeft("EndOfRowLeft");

	public static BeginPainting eBeginPainting = new BeginPainting(
			"BeginPainting");
	public static FinishPainting eStopPainting = new FinishPainting(
			"StopPainting");

	public static DoColDown eDoColDown = new DoColDown("DoColDown");
	public static DoColUp eDoColUp = new DoColUp("DoColUp");
	public static DoRowRight eDoRowRight = new DoRowRight("DoRowRight");
	public static DoRowLeft eDoRowLeft = new DoRowLeft("DoRowLeft");

	public static ChangeColor eChangeColorBLACK = new ChangeColor("BLACK");
	public static ChangeColor eChangeColorRED = new ChangeColor("RED");
	public static ChangeColor eChangeColorBLUE = new ChangeColor("BLUE");
	public static ChangeColor eChangeColorGREEN = new ChangeColor("GREEN");

	public static updateArrived eupdateArrived = new updateArrived("update");

	@Override
	public void runBApplication() {

		// TODO RunBApplication
		bp.add(new MovingUp(), 1.29);
		bp.add(new MovingDown(), 1.28);
		bp.add(new MovingRight(), 1.27);
		bp.add(new MovingLeft(), 1.26);

		bp.add(new MovingRightMaint(), 1.0);
		bp.add(new MovingLeftMaint(), 1.01);
		bp.add(new MovingUpMaint(), 1.02);
		bp.add(new MovingDownMaint(), 1.03);

		bp.add(new MovingRightMi(), 1.10);
		bp.add(new MovingLeftMi(), 1.11);
		bp.add(new MovingUpMi(), 1.12);
		bp.add(new MovingDownMi(), 1.13);

		bp.add(new DoColD2U(), 1.21);
		bp.add(new DoColU2D(), 1.2);
		bp.add(new DoRowL2R(), 1.19);

		bp.add(new EndColDown(), 1.31);
		bp.add(new EndColUp(), 1.3);

		bp.add(new BeginPaintingTheWall(), 20.0);
		bp.add(new StopPaintingTheWall(), 25.0);

		bp.add(new YAxis(), 1.4);
		bp.add(new XAxis(), 1.41);

		bp.add(new ChangePaintingColor(), 5.0);
		bp.add(new ColorControl(), 0.9);

		 bp.add(new BrushMove(), 15.0);

		bp.add(new Wind(), 0.1);
		bp.add(new FixWind(), 1.5);

		bp.add(new IdlingThread(), 100.0);

		bp.startAll();
	}

	static public void main(String arg[]) {

		try {
			BDrone.clear();
			Thread.sleep(10000);

			BProgram.startBApplication(WallPainting.class, "Bdrone");
			Thread Clock = new ClockThread();
			Clock.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
