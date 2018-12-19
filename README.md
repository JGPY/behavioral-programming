![bp.png](https://github.com/JGPY/behavioral-programming/tree/master/data/image/bp.png)

Behavioral Programming (BP) is an approach to software development, which is aligned with how people often describe a systems' behavior. BP is an extension and generalization of scenario-based programming originally introduced with the language of live sequence charts (LSC). Besides LSC, the approach has been implemented in several conventional programming languages, including, Java, Erlang, C, C++ and Javascript.

The modules of a behavioral programming application are threads of behavior, or scenarios (the exact term may vary between languages), which can be thought of as corresponding to very detailed use-cases appearing in a requirements document. Each of these threads of behavior represents a sequence of events that the system should, could or shouldn't carry out, under certain conditions or following certain sequences of events. The threads can be anywhere between being independent or highly dependent on others. And all the threads are interwoven at run-time, yielding an integrated system behavior.

For example, in a game-playing application, each of the game rules, and each of the playing strategies would be programmed separately and independently with little or no explicit awareness of other modules. At run time a common execution mechanism interlaces the behaviors, choosing events based on each module's demands and constraints, taking into account higher level considerations that might include priorities, learning, and planning, among others.

Current behavioral programming tools include, among others, an integrated Eclipse development environment for LSC called PlayGo, which is based on an earlier tool called the Play-Engine, libraries for native development in Java and Erlang, support for natural language development, a prototype model-checking tool, and various visualization and debugging tools.

Behavioral programming integrates with, and complements, object oriented programming, aspect oriented programming, agent-based architectures, agile programming and several other software engineering paradigms. The underlying principles of the approach are to a large extent language-independent and can be implemented in a wide range of environments.

##### Language-independent Principles
Repeatedly, behavior threads declare their "opinions" as to what is desired, what is possible, and what is forbidden, and a central decision is made that accommodates these opinions.

* Event-based abstraction
* Behavior threads
* B-thread independence
* Implicit permission seeking
* Synchronization
* Requesting
* Requesting alternative events
* Blocking
* Event selection with a global view
* Listening
* Event-based-progression
* Unification
* Inter-object
* Environment Interaction 

##### Event-based abstraction
An execution/run of the programmed system can be viewed as a sequence of events. The events can be generated by the system's modules, as well as by the user or the environment.

##### Behavior threads
The system's modules are behaviors/programs/plans/scenarios, which can be viewed externally as "programs running in parallel, i.e., simultaneously", or as "rules that continuously govern the execution". These modules are called behavior threads (b-threads for short).

##### B-thread independence
Ideally, b-threads communicate with each other via agreed-upon events, and there is no direct message exchange between them. These behaviors are generally oblivious to the existence of the other b-threads, and to each of them all other b-threads appear as one. Although, of course, it is possible to craft events that identify their originating behavior. Moreover, since behavioral programming can be integrated into general programming languages, nothing prevents the developer from introducing data structures that can be shared between b-threads.

##### Implicit permission seeking
A b-thread generates events only in ways that allow other b-threads to forbid or override them. Thus a b-thread's behavior can be interpreted as always asking for permission before doing something.

##### Synchronization
The behavioral programming infrastructure causes b-threads to synchronize with each other (e.g., waiting for each other) before generating events. This gives b-threads the opportunity to forbid or override each other's events. The synchronization is automatic and occurs at points marked by simple programming idioms, without burdening the logic of the application that is specific to the b-thread.

##### Requesting
When a b-thread generates an event it recognizes this as merely putting forward a request for consideration in the execution, and it is prepared to handle situations where the event will in fact not be triggered, or will be postponed, briefly or indefinitely. A b-thread may wish to wait indefinitely until the event is triggered, and the system, in turn, is not negatively affected by a large number of such waiting b-threads. Alternatively, a b-thread may monitor certain events that occur as it waits for the triggering of its requested event, and then withdraw its own request before therequested events are actually triggered.

##### Requesting alternative events
a b-thread, or a collection of b-threads for a particular task, can request some (or all) of the events that contribute to the attainment of the goal. This allows them to progress even if some of these alternatives are forbidden by other b-threads.

###### Blocking
A b-thread may forbid the triggering of events that are requested by other b-threads. The forbidding idioms will work correctly even if no such requests are made, or even if there no other b-threads at all.

##### Event selection with a global view
Only events that are requested and not blocked can be triggered.

##### Listening
B-threads may listen out for, and react to, triggered events that they did not request, including changing outstanding requests and declarations of forbidden events.

##### Event-based-progression
A b-thread can progress past a synchronization point when an event that it requested or waited for is triggered.

##### Unification
When a selected event is requested by two or more b-threads, all b-threads requesting it are notified (in addition to those who are only listening-out for it). Each requesting b-thread will advance in the same manner as it would have had it been the only requester. If the event is associated with some execution external to the b-threads, such as logging or execution of an associated method, this processing/effect will occur only once.

##### Inter-object
B-threads are orthogonal to objects. A b-thread may represent "a behaving object", or may describe an inter-object scenario that is not anchored to a particular object.

##### Environment Interaction
B-threads can use standard interfaces to their environment (e.g., access physical sensors and actuators) in order to translate external occurrences into behavioral events and vice versa.

### Selected References
(in ascending year order)

For additional material see bibliographies in papers below and web-pages of team members (see list in inroduction presentation).

    W. Damm and D. Harel. "LSCs: Breathing Life into Message Sequence Charts." J. on Formal Methods in System Design, 19(1), 2001.
    D. Harel, H. Kugler, R. Marelly, and A. Pnueli. "Smart Play-out of Behavioral Requirements." In FMCAD, 2002.
    D. Harel and R. Marelly. "Come, Let’s Play: Scenario-Based Programming Using LSCs and the Play-Engine". Springer, 2003.
    D. Harel. Can Programming Be Liberated, Period? IEEE Computer, 41(1), 2008.
    D. Harel, S. Maoz, S. Szekely, and D. Barkan. "PlayGo: towards a comprehensive tool for scenario based programming." In ASE, 2010.
    D. Harel, A. Marron, and G. Weiss. "Programming coordinated scenarios in Java." In ECOOP , 2010.
    G. Wiener, G. Weiss, and A. Marron. "Coordinating and visualizing independent behaviors in Erlang." In 9th ACM SIGPLAN Erlang Workshop, 2010.
    S. Maoz, D. Harel, and A. Kleinbort, "A Compiler for Multi-Modal Scenarios: Transforming LSCs into AspectJ", ACM TOSEM , Vol. 20, Issue 4, 2011.
    D. Harel, R. Lampert, A. Marron, and G. Weiss. "Model-checking behavioral programs." In EMSOFT , 2011.
    D. Harel, A. Marron and G. Weiss, “Behavioral Programming”, Communications of the ACM, Vol 55. No. 7 , 2012 
    
###  References  
[http://www.wisdom.weizmann.ac.il/~bprogram/index.html](http://www.wisdom.weizmann.ac.il/~bprogram/index.html)