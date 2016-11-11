package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 *
 * <task_queued compile_id='161' method='java/nio/DirectLongBufferU get (I)J' bytes='16' count='256' iicount='256' level='3' stamp='0,201' comment='tiered' hot_count='256'/>
 */
public class TaskQueuedEvent implements TTYEvent
{
    private final static String START_TOKEN = "<task_queued";

    private int compileId;
    private String method;
    private int bytes;
    private int count;
    private int iicount;
    private int level;
    private double stamp;
    private String comment;
    private int hotCount;

    public int getCompileId()
    {
        return compileId;
    }

    public void setCompileId(int compileId)
    {
        this.compileId = compileId;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public int getBytes()
    {
        return bytes;
    }

    public void setBytes(int bytes)
    {
        this.bytes = bytes;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getIicount()
    {
        return iicount;
    }

    public void setIicount(int iicount)
    {
        this.iicount = iicount;
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

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public int getHotCount()
    {
        return hotCount;
    }

    public void setHotCount(int hotCount)
    {
        this.hotCount = hotCount;
    }

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

    @Override
    public void processLine(String line)
    {

    }
}
