package com.focusit.jitloganalyzer.model.tty.events;

/**
 * Created by doki on 08.11.16.
 *
 * <task_queued compile_id='161' method='java/nio/DirectLongBufferU get (I)J' bytes='16' count='256' iicount='256' level='3' stamp='0,201' comment='tiered' hot_count='256'/>
 */
public class TaskQueuedEvent implements TTYEvent
{
    private int compileId;
    private String method;
    private int bytes;
    private int count;
    private int iicount;
    private int level;
    private double stamp;
    private String comment;
    private int hotCount;
}
