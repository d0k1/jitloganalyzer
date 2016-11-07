package com.focusit.jitloganalyzer.model.tty.events;

/**
 * Created by doki on 08.11.16.
 * <sweeper state='finished' traversals='1'  total_blobs='417' nmethods='153' adapters='179' free_code_cache='250194688' stamp='0,198'/>
 */
public class SweeperEvent implements TTYEvent
{
    private String state;
    private int traversals;
    private int totalBlobs;
    private int nmethods;
    private int adapters;
    private long freeCodeCache;
    private double stamp;
}
