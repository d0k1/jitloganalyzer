package com.focusit.experiments.fqn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by dkirpichenkov on 13.12.16.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Measurement(time = 10)
public class FqnBenchmark2
{
    public static final String FILES_BIN = "trees" + File.separator + "dirs.bin";
    public ArrayList<FastFqn> fqns = new ArrayList<>();
    Random r;
    int High;
    int Low;

    @Setup
    public void setup() throws IOException, ClassNotFoundException
    {
        r = new Random();
        try (FileInputStream fos = new FileInputStream(new File(FILES_BIN)))
        {
            try (ObjectInputStream oos = new ObjectInputStream(fos))
            {
                ArrayList<String> files = new ArrayList<>();
                files.addAll((List)oos.readObject());
                files.forEach(item -> fqns.add(FastFqn.fromString(item)));
            }
        }
        r = new Random();
        Low = 2;
        High = fqns.size() - 1;
    }

    @Benchmark
    public void testHashCodes(Blackhole bh)
    {
        bh.consume(fqns.get(0).hashCode());
    }

    @Benchmark
    public void testEqualsCodes(Blackhole bh)
    {
        boolean eq = fqns.get(0).equals(fqns.get(fqns.size() - 1));
        bh.consume(eq);
    }
}
