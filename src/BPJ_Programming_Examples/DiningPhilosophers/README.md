
## Reference source
http://wiki.weizmann.ac.il/bp/index.php/Dining_Philosophers

### Contents
1. Dining Philosophers
2. Problem Description
    1. Events
    2. B-Threads

### Dining Philosophers
In this example one can see (a) how multiple behaviors coexist independently of each other, with very simple rules for each and (b) how model-checking can help find conflicts and other desired and undesired properties.

The example can be found in the BPJ project in the source package sourceCode.bp.state.unittest.ph .

### Problem Description
In the famous dining philosophers problem several philosophers are sitting at a circular table, and are either eating or thinking. At the center is a large bowl of spaghetti, which requires two forks to serve and to eat. A fork is placed in between each pair of adjacent philosophers. Each philosopher may only use the fork to her left and the fork to her right. When a philosopher finishes eating, she puts down the two forks and begins thinking again. Our present goal is to check whether deadlocks, or other starvation conditions, may occur under various philosopher behaviors.

The problem is programmed/modeled behaviorally as follows:

### Events
The events are the picking up and the putting down of a given fork by a given philosopher (e.g., PickUp-F2-by-P2 or PutDown-F1-by-P2)

### B-Threads
There is a b-thread for the behavior of each philosopher and a b-thread for each fork. In the classical version, each philosopher b-thread repeatedly requests the sequence of events representing her picking up the fork to her right, picking up the fork to her left, putting down the right-hand fork, and then putting down the left-hand one. Each fork's b-thread repeatedly waits for an event of picking up the fork by either of its two adjacent philosophers and then blocks its picking up (again)until the fork is put down.

The application assists the model-checking search by labeling the four philosopher behavior states T(thinking -- forks down), 1 (one fork up), E (eating -- two forks up) and F (finished eating -- one fork down).

The fork b-thread states are D (down) and U (up).

We use model-checking to look for violations of liveness properties, and detect the deadlock conditions that are possible under this classical behavior.

For example, a path to a deadlock state in the case of 3 philosophers is displayed as:

    Verification failed:
    init->[T, D, T, D, T, D]
    PickUp-F2-by-P2->[T, D, T, D, 1, U]
    PickUp-F1-by-P1->[T, D, 1, U, 1, U]
    PickUp-F0-by-P0->[1, U, 1, U, 1, U]
    [1, U, 1, U, 1, U] is a deadlock state

where each line of the form "<event> -> <State>" describes a composite states of the entire program along the path and the event whose triggering led the program to transition from the preceding state to the current.

In the symmetry breaking approach one of the philosophers is left-handed, and thus first picks up the fork to her left, rather than the one to her right. As expected, this setup is then proven by model-checking to be deadlock-free, following checking of all possible states.

For liveness testing, the philosophers behavior b-threads marks the non-eating states as hot, and the model-checker is then able to detect the starvation conditions that are possible if fairness is weak. 