
## Dependency package
* commons-javaflow.jar http://commons.apache.org/sandbox/commons-javaflow/


## Reference source
http://wiki.weizmann.ac.il/sourceCode.bp/index.php/Water_Tap

## Contents

1. Alternating Taps Example
    1. Events
    2. b-Threads
    3. Source Code

## Alternating Taps Example

Below is the full source code of the alternating water taps example. It is part of the BPJ package.

### Events

* addHot
* addCold

### b-Threads

* addHotThreeTimes: requests addHot 3 times
* addColdThreeTimes: requests addCold 3 times
* Interleave: Causes alternation between addHot and addCold events by blocking one while waiting for the other and vice versa.

An additional b-thread DisplayEvents (commented out) indicate how one could use these events to drive external outputs, such as real water tap actuators.

### Source Code
