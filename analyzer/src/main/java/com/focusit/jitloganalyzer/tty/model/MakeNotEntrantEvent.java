package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 * <make_not_entrant thread='139673278519040' compile_id='4' compiler='C1' level='3' stamp='0,123'/>
 */
public class MakeNotEntrantEvent extends AbstractTTYEvent
        implements TTYEvent, HasCompileId, HasCompiler, HasStamp, HasThreadId
{
    private final static String START_TOKEN = "<make_not_entrant";

    public long getThreadId()
    {
        return Long.parseLong((String)attributes.get("thread"));
    }

    public long getCompileId()
    {
        return Long.parseLong((String)attributes.get("compile_id"));
    }

    public String getCompiler()
    {
        return (String)attributes.get("compiler");
    }

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

}
