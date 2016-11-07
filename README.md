# Another JVM JIT Log parser

Project helps to find out what happened with JVM's JIT during an application execution.
There are some similar projects JITWatch (https://github.com/AdoptOpenJDK/jitwatch) LogCompilation (https://github.com/headius/LogCompilation)
Reasons that lead me to make this project:
* Performance. I need tool can process with huge JIT's log files, from hundreds of MBs to GBs.
* Extensibility. I want to have a tool that gives some points to customize it's processing behaviour via groovy scripts.
