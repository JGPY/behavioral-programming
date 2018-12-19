To Open the Simulator, Run ServerSide.MainPanel as your main class. After the frame fully load, run your BP program. you may also want to change the port you use (defult is 19999). In your BP program, make sure you send your packages to the right IP and port.

PROTOCOL:
METHOD | X | Y | Color 

METHOD = "CLEARSIM"
Builds new empty frame.
METHOD = "FIXED":
moves the Helicopter to the attached X,Y. The Color will be the color you sent in the 4th parameter.
METHOD = "REL":
moves the Helicopter Relativly to the X,Y you sent. The Color will be the color you sent in the 4th parameter.

 