package com.focusit.experiments.uuidgenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by doki on 15.12.16.
 */
public class UUIDGenerator3
{
    private static final boolean isGWT;
    private static final UUIDGenerator3 INSTANCE;
    private static final String ZEROS = "0000000000000000";

    static
    {
        isGWT = GWT.isScript();
        INSTANCE = new UUIDGenerator3();
    }

    private final char digits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    private final AtomicLong count = new AtomicLong();
    private final char random[];

    public UUIDGenerator3()
    {
        if (isGWT)
        {
            random = null;
        }
        else
        {
            random = format(System.identityHashCode(this), 12);
        }
    }

    public static final UUIDGenerator3 get()
    {
        return INSTANCE;
    }

    public final String nextUUID()
    {
        if (isGWT)
        {
            return nextUUID_JS();
        }

        FastUUID uuid = new FastUUID();
        uuid = build(uuid);
        return uuid.toString();
    }

    private FastUUID build(FastUUID uuid)
    {
        buildByTime(uuid);

        buildByCount(uuid);

        uuid.setPart4(random);
        return uuid;
    }

    private void buildByCount(FastUUID uuid)
    {
        long currentCount = count.incrementAndGet();
        part2(uuid, currentCount);
        part3(uuid, currentCount);
    }

    private void part2(FastUUID uuid, long currentCount)
    {
        uuid.setPart2(format(currentCount & 0xFFFFl, 4));
    }

    private void part3(FastUUID uuid, long currentCount)
    {
        uuid.setPart3(format((currentCount & 0xFFFF0000l) >> 16, 4));
    }

    private void buildByTime(FastUUID uuid)
    {
        long time = System.currentTimeMillis();
        part0(uuid, time);
        part1(uuid, time);
    }

    private void part1(FastUUID uuid, long time)
    {
        uuid.setPart1(format((time & 0xFFFF0000000l) >> 28, 4));
    }

    private void part0(FastUUID uuid, long time)
    {
        uuid.setPart0(format(time & 0xFFFFFFFl, 7));
    }

    private char[] format(long value, int length)
    {
        return fillValue(length, getStrValue(value, length));
    }

    private char[] fillValue(int length, char[] strValue)
    {
        char result[] = new char[length];
        System.arraycopy(strValue, strValue.length - length, result, 0, length);
        return result;
    }

    private char[] getStrValue(long value, int length)
    {
        return longAsString(value);
    }

    private char[] longAsString(long val)
    {
        char result[] = new char[16];

        short pos;

        for (int i = 0; i < 16; i++)
        {
            pos = (short)(val & 0xF);
            val = val >> 4;
            result[15 - i] = digits[pos];
        }
        return result;
    }

    private String nextUUID_JS()
    {
        return "";
    }
}
