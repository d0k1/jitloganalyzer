package com.focusit.experiments.fqn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Do not forget to add important arguments to JVM:
 * -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:LogFile=./experiment_fqn01.log
 * Created by dkirpichenkov on 13.12.16.
 */
public class ExperimentFqn
{
    public List<String> files = new ArrayList<>();

    public static final String FILES_BIN = "trees" + File.separator + "dirs.bin";

    public void load() throws IOException, ClassNotFoundException
    {
        try (FileInputStream fos = new FileInputStream(new File(FILES_BIN)))
        {
            try (ObjectInputStream oos = new ObjectInputStream(fos))
            {
                files.addAll((List)oos.readObject());
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        ExperimentFqn experiment = new ExperimentFqn();
        experiment.load();

        ArrayList<FastFqn> filesFqn = new ArrayList<>();
        experiment.files.forEach(file -> filesFqn.add(FastFqn.fromString(file)));

        System.out.println("Files: " + filesFqn.size());

        final long[] hashes = { 0L };

        filesFqn.forEach(fqn -> hashes[0] += fqn.hashCode());

        final int[] equals = { 0 };
        filesFqn.forEach(fqn1 -> filesFqn.forEach(fqn2 -> {
            if (fqn1.equals(fqn2))
            {
                equals[0]++;
            }
        }));

        System.out.println("Sum of hashes: " + hashes[0]);
        System.out.println("Equalses : " + equals[0]);
    }
}
