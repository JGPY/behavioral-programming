
## Dependency package
oldVersion.tictactoe
    └── bp.jar

tictactoe
   └── il.ac.wis.cs.bpj_1.1.0.jar
        ├── bp
        └── lib
            ├── ant.jar
            ├── asm-3.3.jar
            ├── asm-analysis-3.3.jar
            ├── asm-commons-3.3.jar
            ├── asm-tree-3.3.jar
            ├── asm-util-3.3.jar
            ├── asm-xml-3.3.jar
            ├── bcel-5.2.jar
            ├── commons-io-2.0.jar
            ├── commons-jci-core-1.0.jar
            ├── commons-logging-1.1.1.jar
            ├── Javaflow.jar
            ├── junit-4.8.2.jar
            ├── junit-addons-1.4.jar
            └── junitx-5.1.jar


### Reference source
http://wiki.weizmann.ac.il/sourceCode.bp/index.php/Tic-Tac-Toe

## Contents
1. A behavioral program for the game of Tic-Tac-Toe
    1. Game Description
    2. b-Threads
        1. Rules of the game
        2. Strategies
    3. Other Classes
    4. Design Notes
    5. Download

## A behavioral program for the game of Tic-Tac-Toe

### Game Description
Two players, X and O, alternately mark squares on a 3X3 grid whose squares are identified by <row,column> pairs: <0,0>, <0,1>, ...,<2,2>. The winner is the player who manages to form a full horizontal, vertical or diagonal line with three of his/her marks. If the entire grid becomes marked but no player has formed a line, the result is a draw.

In our example, player X should be played by a human user, and player O is played by the application. Each move (marking of a square by a player) is represented by an event, X_<row,col> or O_<row,col>. Three additional events, XWin, OWin, and draw$, represent the respective victories and a draw.

A play of the game may be described as a sequence of events. E.g., the sequence X_<0,0>, O_<1,1>, X_<2,1>, O_<0,2>, X_<2,0>, O_<1,0>, X_<2,2>, XWin describes a play in which X wins, and whose final configuration is:

TTT01.jpg

### b-Threads
Below are listed the b-threads of the application.

#### Rules of the game
* SquareTaken: block further marking of a square already marked by X or O.
* EnforceTurns: alternately block O moves while waiting for X moves, and vice versa (we assume that X always plays first).
* DetectXWin: wait for placement of three X marks in a line and request XWin.
* DetectOWin: wait for placement of three O marks in a line and request OWin.
* DetectDraw: wait for nine moves and request draw event.

#### Strategies
* DefaultMoves: a b-thread that repeatedly requests all nine possible O moves in the following order of center, corners and edges: O_<1,1>,O_<0,0>,O_<0,2>,O_<2,0>,O_<2,2>, O_<0,1>,O_<1,0>,O_<1,2>,O_<2,1>.
* PreventThirdX: when two Xs are noticed in a line, add an O in that line (and prevent an immediate loss).
* AddThirdO: when two Os are located on a single line, add a third O (and win).
* PreventXFork: when two Xs are noticed, in a corner and opposing edge such that X may force a win, mark an O in the intersection corner of the potential fork, thus preventing its creation.
* PreventAnotherXFork: when the first two Xs are marked in two opposite corners and the first O is marked at the center, request O_<0,1>. In the spirit of ``the best defense is a good offense, this move creates an attack that forces X to play X_<2,1>, and seems to avoid the immediate fork threat.
* PreventYetAnotherXFork: when two Xs are noticed in two edge squares that are adjacent to a common corner, mark an O in that corner.

### Other Classes
* ExternalApp: The main class of the application. Loads and starts the b-threads
* GUI: Manages the display

### Design Notes
For some b-threads such as DefaultMoves there is a single instance. Other b-threads, such as AddThirdO are parametric, and there is a separate instance of the b-thread for each ordering of the three events in each row, column and diagonal. Thus, in the present design such b-threads do not maintain a copy of the board, but instead are simply waiting for very specific two events in a given order and responding with other events. Other designs are of course possible> for example a b-thread can be responsible for three events in any order, wait for any two in any order and then request the third. In another design a b-thread can maintain a copy of the board, wait for almost any event, keep track of board configuration changes, look for lines to complete, and request the corresponding move events.

### Download
1. Click here[](http://wiki.weizmann.ac.il/sourceCode.bp/images/sourceCode.bp/TicTacToe.zip) to download the tic-tac-toe example.
2. Installation Instructions:
    1. Import the downloaded project to your workspace:
        1. From the 'File' menu choose 'Import'-->'General'-->'Existing Projects into Workspace'.
        2. Click the 'Next' button.
        3. Click the 'Select archive file' option and then click the 'Browse...' button to select the downloaded zip file.
        4. Click 'Finish'.
    2. The tic-tac-toe example project is now part of your workspace.
    3. Refer to the readme.txt file for execution instructions.
