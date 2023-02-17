# JMH integration with Spring

## What is JMH?
Java Microbench mark Harness is a very nice, free tool which allows you to create Java performance tests. This set of blog posts shows how we have used it to set up a set of performance tests. In our case, we wanted to run some tests of other Java Image libraries against our own JDeli image library. The tests need to be:

* Fair
* Replicable
* Consistent
To achieve this we have created a set of tests using JMH (Java Microbench mark Harness), some code examples we have put on GitHub and some sample images which are available on the web. The tests can be run from Java, directly. In our case, we have also chosen to run them from the IDEA IDE (which has some very nice JMH integration).

### Prerequisites
In order to run the tests, you will need:

* Java8 or above
* IDEA (free or commercial version)
* The Image libraries we are testing
* A copy of our code from GitHub
* The sample images to test

Please refer to the full documentation:
[JMH – Java Microbenchmark Harness](https://openjdk.org/projects/code-tools/jmh/)

## Demo
The project is based on the following Spring Application, in order to fully understand the different levels of micro benchmark.

### In order to run the JMHSpringDataDemo through IDE:
JMHSpringDataDemo should have the following as parameters which are configurable:

forkNum=1;warmIterNum=1;warmTime=1;measurementIterations=1;numThreads=1;numUsers=10

### Reports
All reports will be created inside of `./reports` folder. The current format is JSON, it allows open in [JHM Visualizar](https://jmh.morethan.io/) tool.  
