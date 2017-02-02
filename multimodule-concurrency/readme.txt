Volatile:

Declaring a volatile Java variable means:
* The value of this variable will never be cached thread-locally: all reads and writes will go straight to "main memory";
* Access to the variable acts as though it is enclosed in a synchronized block, synchronized on itself.

First, the easy cases where you basically don't need volatile or any other synchronization mechanism:
* volatile is not necessary– or in fact possible– for fields that are immutable (declared final);
* volatile is not necessary for variables that are accessed by only one thread (though of course you have to make a correct decision that they are only accessed by one thread!);
* volatile is not suitable for complex operations where you need to prevent access to a variable for the duration of the operation: in such cases, you should use object synchronization or one of Java 5's explicit lock classes added.

