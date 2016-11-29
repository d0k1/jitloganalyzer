package com.focusit.jitloganalyzer.tty.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 08.11.16.
 * <sweeper state='finished' traversals='1'  total_blobs='417' nmethods='153' adapters='179' free_code_cache='250194688' stamp='0,198'/>
 */
public class SweeperEvent implements TTYEvent
{
    private final static String START_TOKEN = "<sweeper";

    private String state;
    private int traversals;
    private int totalBlobs;
    private int nmethods;
    private int adapters;
    private long freeCodeCache;
    private double stamp;

    private final static Pattern pattern = Pattern.compile(
            "sweeper\\s+state='(\\w+)'.+traversals='(\\d+)'\\s+total.blobs='(\\d+)'.+nmethods='(\\d+)'.+adapters='(\\d+)'.+free.code.cache='(\\d+)'.+stamp='(.+)'\n");

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public int getTraversals()
    {
        return traversals;
    }

    public void setTraversals(int traversals)
    {
        this.traversals = traversals;
    }

    public int getTotalBlobs()
    {
        return totalBlobs;
    }

    public void setTotalBlobs(int totalBlobs)
    {
        this.totalBlobs = totalBlobs;
    }

    public int getNmethods()
    {
        return nmethods;
    }

    public void setNmethods(int nmethods)
    {
        this.nmethods = nmethods;
    }

    public int getAdapters()
    {
        return adapters;
    }

    public void setAdapters(int adapters)
    {
        this.adapters = adapters;
    }

    public long getFreeCodeCache()
    {
        return freeCodeCache;
    }

    public void setFreeCodeCache(long freeCodeCache)
    {
        this.freeCodeCache = freeCodeCache;
    }

    public double getStamp()
    {
        return stamp;
    }

    public void setStamp(double stamp)
    {
        this.stamp = stamp;
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
            state = matcher.group(1);
            traversals = Integer.parseInt(matcher.group(2));
            totalBlobs = Integer.parseInt(matcher.group(3));
            nmethods = Integer.parseInt(matcher.group(4));
            adapters = Integer.parseInt(matcher.group(5));
            freeCodeCache = Integer.parseInt(matcher.group(6));
            stamp = Double.parseDouble(matcher.group(2).replace(",", "."));
        }
        else
        {
            System.err.println("Caanot find match for '" + line + "' by " + pattern.pattern());
        }
    }
}
