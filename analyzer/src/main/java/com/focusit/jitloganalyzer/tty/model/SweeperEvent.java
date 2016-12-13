package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 * <sweeper state='finished' traversals='1'  total_blobs='417' nmethods='153' adapters='179' free_code_cache='250194688' stamp='0,198'/>
 */
public class SweeperEvent extends AbstractTTYEvent implements TTYEvent, HasStamp
{
    private final static String START_TOKEN = "<sweeper";

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

}
