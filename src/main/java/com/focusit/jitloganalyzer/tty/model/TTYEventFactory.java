package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 10.11.16.
 */
public class TTYEventFactory
{
    public static TTYEvent getEventForString(String line)
    {
        TTYEvent event;
        if ((event = new ClassLoadEvent()).suitable(line))
        {
            return event;
        }

        if ((event = new MakeNotEntrantEvent()).suitable(line))
        {
            return event;
        }

        if ((event = new NMethodEvent()).suitable(line))
        {
            return event;
        }

        if ((event = new SweeperEvent()).suitable(line))
        {
            return event;
        }

        if ((event = new TaskQueuedEvent()).suitable(line))
        {
            return event;
        }

        if ((event = new UncommonTrapEvent()).suitable(line))
        {
            return event;
        }

        if ((event = new WriterEvent()).suitable(line))
        {
            return event;
        }
        return null;
    }
}
