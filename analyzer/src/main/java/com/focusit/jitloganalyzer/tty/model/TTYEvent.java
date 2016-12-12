package com.focusit.jitloganalyzer.tty.model;

/**
 * Common TTYEvent that can parse a log line and do something with founded data
 * 
 * Created by doki on 08.11.16.
 */
public interface TTYEvent
{
    boolean suitable(String line);

    void processLine(String line);
}
