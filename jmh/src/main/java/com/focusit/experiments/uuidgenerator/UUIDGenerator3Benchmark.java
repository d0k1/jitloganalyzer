package com.focusit.experiments.uuidgenerator;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by doki on 13.12.16.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Measurement(time = 10)
public class UUIDGenerator3Benchmark
{
    @Benchmark
    public void testUUIDGenerator(Blackhole bh)
    {
        UUIDGenerator3 generator = UUIDGenerator3.get();
        String result = generator.nextUUID();
        bh.consume(result);
    }

}
