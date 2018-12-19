
### Reference source
http://wiki.weizmann.ac.il/sourceCode.bp/index.php/Flight_of_a_flock_of_birds

### Simulating Flight of a Flock of Birds
In this example we simulate the flight of a flock of birds, with each bird having a simple set of rules implemented as b-threads. The example highlights

* Multi-agent activity
* Emergent group behaviors - emanating from individual agent behavior
* Implementation of animation with BPJ - and handling of associated design issues.
* [Movie: 25 birds gathering and flying](http://www.wisdom.weizmann.ac.il/~bprogram/videos/flock25Gather.avi)
* [Movie: Birds disperse in reaction to a "scare" action](http://www.wisdom.weizmann.ac.il/~bprogram/videos/flockScare.mp4)

### Requirements - from each bird
* Always try to move towards the center of the flock
* Always avoid colliding with other birds
* When getting close to a wall - change direction - in the same "reflection" angle
* When the "Scare" button is hit - fly away from the center of the flock.

### Download
1. Click [here](http://wiki.weizmann.ac.il/sourceCode.bp/images/sourceCode.bp/Flock.zip) to download the flock of birds example.
2. Installation Instructions:
    1. Import the downloaded project to your workspace:
        1. From the 'File' menu choose 'Import'-->'General'-->'Existing Projects into Workspace'.
        2. Click the 'Next' button.
        3. Click the 'Select archive file' option and then click the 'Browse...' button to select the downloaded zip file.
        4. Click 'Finish'.
    2. The flock of birds example project is now part of your workspace.
    3. Refer to the readme.txt file for execution instructions.
