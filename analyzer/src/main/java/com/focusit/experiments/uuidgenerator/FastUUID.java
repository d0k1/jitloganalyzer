package com.focusit.experiments.uuidgenerator;

/**
 * Created by doki on 12.12.16.
 */
public final class FastUUID
{
    private final char uuid[] = new char[36];

    public FastUUID()
    {
        uuid[0] = 'f';
        uuid[8] = '-';
        uuid[13] = '-';
        uuid[18] = '-';
        uuid[23] = '-';
    }

    public final void setPart0(char p0[])
    {
        System.arraycopy(p0, 0, uuid, 1, 7);
    }

    public final void setPart1(char p1[])
    {
        System.arraycopy(p1, 0, uuid, 9, 4);
    }

    public final void setPart2(char p2[])
    {
        System.arraycopy(p2, 0, uuid, 14, 4);
    }

    public final void setPart3(char p3[])
    {
        System.arraycopy(p3, 0, uuid, 19, 4);
    }

    public final void setPart4(char p4[])
    {
        System.arraycopy(p4, 0, uuid, 24, 12);
    }

    public final String toString()
    {
        return new String(uuid);
    }
}
