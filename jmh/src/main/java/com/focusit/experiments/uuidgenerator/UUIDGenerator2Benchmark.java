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
public class UUIDGenerator2Benchmark
{
    @Benchmark
    public void testUUIDGenerator(Blackhole bh)
    {
        UUIDGenerator2 generator = UUIDGenerator2.get();
        String result = generator.nextUUID();
        bh.consume(result);
    }

}
