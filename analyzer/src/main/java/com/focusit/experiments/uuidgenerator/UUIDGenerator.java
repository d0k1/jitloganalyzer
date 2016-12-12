package com.focusit.experiments.uuidgenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Do not forget to add important arguments to JVM:
 * -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:LogFile=./experiment_uuid01.log
 *
 * Created by doki on 12.12.16.
 */
public class UUIDGenerator
{
    private static final UUIDGenerator INSTANCE = new UUIDGenerator();
    private static final String ZEROS = "0000000000000000";
    private final AtomicLong count = new AtomicLong();
    private final String random;

    public UUIDGenerator()
    {
        if (GWT.isScript())
        {
            random = format((long)(Math.floor(Math.random() * 4294967296L) - 2147483648L), 12);
        }
        else
        {
            random = format(System.identityHashCode(this), 12);
        }
    }

    public static UUIDGenerator get()
    {
        return INSTANCE;
    }

    public String nextUUID()
    {
        if (GWT.isScript())
        {
            return nextUUID_JS();
        }
        long time = System.currentTimeMillis();
        long currentCount = count.incrementAndGet();

        String p0 = "f" + format(time & 0xFFFFFFFl, 7);
        String p1 = format((time & 0xFFFF0000000l) >> 28, 4);
        String p2 = format(currentCount & 0xFFFFl, 4);
        String p3 = format((currentCount & 0xFFFF0000l) >> 16, 4);

        return p0 + "-" + p1 + "-" + p2 + "-" + p3 + "-" + random;
    }

    String format(long value, int length)
    {
        String strValue;
        if (GWT.isScript())
        {
            //профайлер IE, firefox показывают, что toHexString реальный buttleneck для клиента
            strValue = String.valueOf(value > 0 ? value : Math.abs(value + 1));
            strValue = strValue.substring(0, Math.min(length, strValue.length()));
        }
        else
        {
            strValue = Long.toHexString(value);
        }
        return ZEROS.substring(0, length - strValue.length()) + strValue;
    }

    String nextUUID_JS()
    {
        return "";
    }
}