package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 * <make_not_entrant thread='139673278519040' compile_id='4' compiler='C1' level='3' stamp='0,123'/>
 */
public class MakeNotEntrantEvent implements TTYEvent
{
    public final static String START_TOKEN = "<make_not_entrant";

    private long threadId;
    private long compileId;
    private String compiler;
    private int level;
    private double stamp;

    public long getThreadId()
    {
        return threadId;
    }

    public void setThreadId(long threadId)
    {
        this.threadId = threadId;
    }

    public long getCompileId()
    {
        return compileId;
    }

    public void setCompileId(long compileId)
    {
        this.compileId = compileId;
    }

    public String getCompiler()
    {
        return compiler;
    }

    public void setCompiler(String compiler)
    {
        this.compiler = compiler;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public double getStamp()
    {
        return stamp;
    }

    public void setStamp(double stamp)
    {
        this.stamp = stamp;
    }

    @Override
    public void processLine(String line)
    {

    }
}