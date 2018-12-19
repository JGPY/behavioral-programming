This example implements a drone painting a wall. It runs in 2 modes:
1. With a simple java-based simulator.
2. With a blender-based simulator.

The involved projects:

- BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone - The main project that includes the BThreads that implements the drawing/scanning tasks.
- BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim - The simple java-based simulator.
- il.ac.wis.cs.rovtool - (provided as fatjar under BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone/lib) a library containing an interface for rov (remotely operated vehicle).
- il.ac.wis.cs.rovtool.sourceCode.bp - (provided as fatjar under BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone/lib) a library containing an interface for rov applications written in sourceCode.bp
- il.ac.wis.cs.rovtool.ardrone - (provided as fatjar under BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone/lib) a library containing api specific to ardrone (e.g. analyzing its specific nav data).
- BPJ - (provided as fatjar under BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone/lib) BPJ library
- il.ac.wis.cs.rovtool.blender - The blender-based simulator.

To run the example with a simple java-based simulator:
1. set the following VM argument in the run configuration:
	 -Drunmode=netWorkingMode
2. Run MainPanel from the BPJ_Programming_Examples.Helicopter_flight_and_mission.HeliSim project, under the ServerSide package.
3. Run WallPainting from the BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone project, under the ServerSide package.

To run the example with a blender-based simulator:
1. set the following VM arguments in the run configuration:
	-Drunmode=bpApiMode
	-Drovtool.home=[full path of the BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone project root, e.g., D:\workspaces\ardrone\BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone]
	-Drovtool.drone.type.sim-local
	-Drovtool.sourceCode.bp.fc-by-sourceCode.bp=false
	-Drovtool.spe.type.blender
	-ea

2. Download Blender from here: http://www.blender.org/download/
3. Open (from file browser) the file 'il.ac.wis.cs.rovtool.blender\res\blender\game-engine.blend' in Blender (by simply double clicking the file).
4. In Blender's 3D viewport (the default view), press 'p' to enter 'game-mode' (use Esc to exit this mode).
5. Run WallPainting from the BPJ_Programming_Examples.Helicopter_flight_and_mission.BDrone project, under the ServerSide package.

