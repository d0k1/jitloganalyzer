package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 14.12.16.
 */
public class JVMSEvent extends AbstractTTYEvent implements TTYEvent
{
    private final static String START_TOKEN = "<jvms";

    @Override
    public void setPreviousEvent(TTYEvent event)
    {
        if (event instanceof UncommonTrapEvent)
        {
            UncommonTrapEvent e = (UncommonTrapEvent)event;
            e.addJVMS(this);
        }
    }

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }
}
