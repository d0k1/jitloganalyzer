# Another JVM JIT Log parser

The project helps to find out what happened with JVM's JIT during an application execution.
There are some similar projects:
* JITWatch (https://github.com/AdoptOpenJDK/jitwatch)
* LogCompilation (https://github.com/headius/LogCompilation)

Reasons that lead me to make this project:
* Performance. I need tool can process with huge JIT's log files, from hundreds of MBs to GBs.
* Extensibility. I want to have a tool that gives some points to customize it's processing behaviour via groovy scripts.
* A lot of interest of course ;-)

This tool is Java8 compatible. I don't know how it would work with any other java's compilation log.

## JIT's Log
Java's compilation log consists of two parts: tty (which is for save just event log), compilation_log ()which is detailed explanations of JIT's decisions).
 
The first part, tty, describes application state in common:

* what classes were loaded
* what method were queued to compilation
* what methods were compiled and stored to the code cache region
* what were traps during compiled method execution, if any, of course
* what was marked as 'not entrant'
* when seeping had occurred
* what was cache region usage at some point in time

TTYLog parses this tty part of compilation log, and provides data for further analysis.

## Experiments

This repository has some examples to study JIT's behaviour with this analyzer.
And this repository has some JMH benchmarks based on examples to check out if JIT optimizations matters.

