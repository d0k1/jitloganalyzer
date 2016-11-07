package com.focusit.jitloganalyzer.model.tty.events;

/**
 * Created by doki on 08.11.16.
 * <make_not_entrant thread='139673278519040' compile_id='4' compiler='C1' level='3' stamp='0,123'/>
 */
public class MakeNotEntrantEvent implements TTYEvent
{
    private long threadId;
    private long compileId;
    private String compiler;
    private int level;
    private double stamp;
}
