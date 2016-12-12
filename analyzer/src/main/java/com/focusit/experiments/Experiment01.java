package com.focusit.experiments;

import java.math.BigDecimal;

/**
 * Simple experiment that should show an on-stack-replacement loop replacement
 * Do not forget to add important arguments to JVM:
 * -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:LogFile=./experiment01.log
 * Created by doki on 11.12.16.
 */
public class Experiment01
{
    public static void main(String[] args)
    {
        Experiment01 experiment = new Experiment01();

        BigDecimal result = new BigDecimal(0);
        for (int i = 0; i < 10000 + Math.random() * 1000; i++)
        {
            result = result.add(BigDecimal.valueOf(experiment.foo()));
        }

        System.out.println("Result: " + result.toPlainString());
    }

    public int foo()
    {
        int result = 0;
        for (int i = 1; i < 1024 + Math.random() * 1000; i++)
        {
            result += i * 3;
        }

        return result;
    }

}
