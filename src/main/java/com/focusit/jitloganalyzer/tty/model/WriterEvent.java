package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 * <writer thread='139821162608384'/>
 */
public class WriterEvent extends AbstractTTYEvent implements TTYEvent, HasThreadId
{
    private final static String START_TOKEN = "<writer";

    private long threadId;

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

    @Override
    public void processLine(String line)
    {

    }

    public long getThreadId()
    {
        return threadId;
    }

    public void setThreadId(long threadId)
    {
        this.threadId = threadId;
    }
}
