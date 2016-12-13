package com.focusit.experiments.uuidgenerator;

/**
 * Created by doki on 12.12.16.
 * Do not forget to add important arguments to JVM:
 * -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:LogFile=./experiment_uuid01.log
 */
public class ExperimentUUID
{
    public static void main(String[] args)
    {
        UUIDGenerator2 generator = UUIDGenerator2.get();
        //        UUIDGenerator generator = UUIDGenerator.get();
        int retVal = 0;
        String ideal = "0000";

        for (int i = 0; i < 1002400 * Math.random() * 24250; i++)
        {
            String result = generator.nextUUID();

            if (result.equalsIgnoreCase(ideal))
            {
                System.out.println("Hooray!");
                retVal = 1;
            }
        }

        if (retVal == 1)
        {
            System.out.println("retVal == 1;");
        }
    }
}
