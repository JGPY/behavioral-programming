
Example: TicTacToe

See example description in www.b-prog.org.

Run Configurations: copy VM Arguments below into run configuration definition in Eclipse.

Run configuration for running in playing mode:
Either no VM Arguments or:
-DrunMode=DET

Run configuration for model checking:
-DrunMode=MCSafety
-Dshallow=true
-noverify


### Reference source
http://wiki.weizmann.ac.il/sourceCode.bp/index.php/Sudoku

### Contents
1. <a href="#Sudoku">Sudoku</a> <br>
2. <a href="#Requirements">Requirements<a>
3. <a href="#Events">Events<a>
    1. <a href="#Playing">Playing b-threads<a>
    2. <a href="#b-threads">b-threads that support model-checking<a>
    3. <a href="#Design">Design Notes<a>
    3. <a href="#Download">Download<a>


## <a name="Sudoku">Sudoku</a>

In this example one can see two properties of behavioral programming
- How different heuristics and pieces of knowledge about the problem domain can be used separately to program an application
- How model-checking can be used to help find a desired scenario.

## <a name="Requirements">Requirements</a>
In the cells of a 9X9 grid which is also divided into 9 3X3 boxes, place the digits 1-9 such that in each row, column and box, each digit appears exactly once.

## <a name="Events">Events</a>
- Move: A single event class with 9X9X9 = 721 instances, indicating the writing of a given digit in a given cell. The class has the following data fields:

    * number: the digit written in the cell (1-9)
    * ow: The row of the cell (1-9)
    * col: the column of the cell (1-9)
    * boxrow: The row of the 3X3 box in which this cell resides (1-3)
    * boxcol: the column of the 3X3 box in which this cell resides (1-3)

When enough data is provided, the strategies below are sufficient for solving the game. When not enough data is provided, model checking explores the different possibilities until a solution is found.

#### <a name="Events">Events</a>Playing b-threads
* MakeGivenMoves: This b-thread provides the opening input to the riddle - filling in the known cells.
* DefaultMove: Each of the 81 instances of this b-thread class, is associated with a cell, and requests all possible 9 digits. Once an event occurs, all other events for the cell are blocked.
* DetectWin: This b-thread simply counts until 81 events have been triggered. The correctness of the solution is guaranteed
* EliminateTwoInABox: This b-thread tries to write a number in a cell after the same number was written in two other columns of this box. It does not check if the cell is occopied or if the digit was already written in the present column - this is provided by event-blocking in other b-threads.
* PlaceANumberInARow: With an instance for each digit and each row, this b-thread tries to place a particular digit in the row. It waits till there is only one place to put it, and then requests the corresponding event.
* PlaceANumberInAColumn: With an instance for each digit and each row, this b-thread tries to place a particular digit in the Column. It waits till there is only one place to put it, and then requests the corresponding event.
* PlaceANumberInABox: With an instance for each digit and each place in the box, this b-thread tries to place a particular digit in the box. It waits till there is only one place to put it, and then requests the corresponding event.
* EliminateEight: For each cell, an instance of this b-thread checks the possibilities for the cell, and eliminates possibilities as conflicting events occur elsewhere. When the number of remaining possibilities is one, the b-thread requests the remaining digit.

#### <a name="b-threads">b-threads that support model-checking</a>
PruneWhenNoSolution: This b-thread requests an event at a low priority. If it is triggered, it means that all move events are blocked, hence the game has failed. It then calls the model-checking API to prune the search.


#### <a name="Design">Design Notes</a>
* The b-thread DefaultMove could have been split into two. One that requests the default moves and terminates, and one that waits for any event in a cell and blocks all alternative digits in the cell. The present design works because a pending request of DefaultMove is unified with any granted request due to the stratgies/heuristics b-threads, as well as the GivenMoves b-thread.

### <a name="Download">Download</a>
1. Click [here](http://wiki.weizmann.ac.il/sourceCode.bp/images/sourceCode.bp/Sudoku1.zip) to download the BPJ_Programming_Examples.sudoku example.
2. Installation Instructions:
    1. Import the downloaded project to your workspace:
        1. From the 'File' menu choose 'Import'-->'General'-->'Existing Projects into Workspace'.
        2. Click the 'Next' button.
        3. Click the 'Select archive file' option and then click the 'Browse...' button to select the downloaded zip file.
        4. Click 'Finish'.
    2. The BPJ_Programming_Examples.sudoku example project is now part of your workspace.
    3. Refer to the readme.txt file for execution instructions.
