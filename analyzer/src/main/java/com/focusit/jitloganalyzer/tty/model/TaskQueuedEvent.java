package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 *
 * <task_queued compile_id='161' method='java/nio/DirectLongBufferU get (I)J' bytes='16' count='256' iicount='256' level='3' stamp='0,201' comment='tiered' hot_count='256'/>
 */
public class TaskQueuedEvent extends AbstractTTYEvent implements TTYEvent, HasCompileId, HasStamp, HasMethod
{
    private final static String START_TOKEN = "<task_queued";

    public long getCompileId()
    {
        return Long.parseLong((String)attributes.get("compile_id"));
    }

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

    public String getMethod()
    {
        return (String)attributes.get("method");
    }
}
