package com.focusit.jitloganalyzer.tty.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final static Pattern pattern = Pattern.compile(
            "<task_queued\\s+compile_id='(\\d+)'\\s+method='(.+)?'\\s+bytes='(\\d+)'\\scount='(\\d+)'\\s+iicount='(\\d+)'\\s+level='(\\d+)'\\s+stamp='(.+)?'\\s+comment='(.+)?'\\s+hot_count='(\\d+)'\n");

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
        Matcher matcher = pattern.matcher(line);

        if (matcher.find())
        {
            compileId = Integer.parseInt(matcher.group(1));
            method = matcher.group(2);
            bytes = Integer.parseInt(matcher.group(3));
            count = Integer.parseInt(matcher.group(4));
            iicount = Integer.parseInt(matcher.group(5));
            level = Integer.parseInt(matcher.group(6));
            stamp = Double.parseDouble(matcher.group(7).replace(",", "."));
            comment = matcher.group(8);
            hotCount = Integer.parseInt(matcher.group(9));
        }
    }
}
