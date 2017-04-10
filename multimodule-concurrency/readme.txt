Volatile:

Declaring a volatile Java variable means:
* The value of this variable will never be cached thread-locally: all reads and writes will go
straight to "main memory";
* Access to the variable acts as though it is enclosed in a synchronized block, synchronized on itself.

First, the easy cases where you basically don't need volatile or any other synchronization mechanism:
* volatile is not necessary– or in fact possible– for fields that are immutable (declared final);
* volatile is not necessary for variables that are accessed by only one thread (though of course you
have to make a correct decision that they are only accessed by one thread!);
* volatile is not suitable for complex operations where you need to prevent access to a variable for
the duration of the operation: in such cases, you should use object synchronization or one of Java 5's
explicit lock classes added.

wait/sleep/yield
* sleep and yield are defined in Thread class, wait is defined in Object class
* wait is used mainly for inter-thread communication and sleep is used to pause current thread for
a period of time, when a thread calls wait it releases monitor or lock it was holding
* yield releases CPU hold by thread to give another thread an opportunity to run. Whether the other thread
runs depends on thread scheduler.

shutdown hooks
* a special construct that allows to plug-in a piece of code to be executed when JVM shuts down
* shutdown hooks may not be executed in some cases: crash, SIGKILL/TerminateProcess, Runtime.halt()
* once started shutdown hooks can be forcibly stopped before completion, same as above
* more than one shutdown hook is possible, but their execution order is not guaranteed
* shutdown hooks cannot be registered/unregistered within shutdown hooks
* once shutdown sequence starts, it can be stopped by Runtime.halt() only
* using shutdown hooks requires security permissions
* exceptions thrown by shutdown hooks are treated same as any other exceptions