I run benchmarks with command

java -jar jmh/target/benchmarks.jar  -wi 5 -w 60 -i 5 -r 60 -f 1 UUID

and got

Benchmark                                   Mode  Cnt        Score        Error  Units
UUIDGenerator2Benchmark.testUUIDGenerator  thrpt    5  3302764.490 ± 162890.107  ops/s
UUIDGenerator3Benchmark.testUUIDGenerator  thrpt    5  6050830.537 ±  19781.081  ops/s
UUIDGeneratorBenchmark.testUUIDGenerator   thrpt    5  3072088.105 ±  64408.820  ops/s

