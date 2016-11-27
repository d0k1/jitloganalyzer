package com.focusit.jitloganalyzer.tty.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 08.11.16.
 *
 * <uncommon_trap thread='139673775703808' reason='unstable_if' action='reinterpret' compile_id='354' compiler='C2' level='4' stamp='0,324'>
 *    <jvms bci='563' method='com/sun/org/apache/xerces/internal/impl/XMLEntityScanner scanLiteral (ILcom/sun/org/apache/xerces/internal/xni/XMLString;)I' bytes='714' count='1086' backedge_count='26662' iicount='1086'/>
 * </uncommon_trap>
 */
public class UncommonTrapEvent implements TTYEvent
{
    private final static String START_TOKEN = "<uncommon_trap";

    private long threadId;
    private String reason;
    private String action;
    private String compiler;
    private int compileId;
    private int level;
    private double stamp;

    private final static Pattern pattern = Pattern.compile(
            "uncommon_trap\\s+thread='(\\d+)'\\s+reason='(.+)?'\\s+action='(.+)?'\\s+compile_id='(\\d+)'\\s+compiler='(.+)?'\\s+level='(\\d+)'\\s+stamp='(.+)?'");

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
            threadId = Long.parseLong(matcher.group(1));
            reason = matcher.group(2);
            action = matcher.group(3);
            compileId = Integer.parseInt(matcher.group(4));
            compiler = matcher.group(5);
            level = Integer.parseInt(matcher.group(6));
            stamp = Double.parseDouble(matcher.group(7).replace(",", "."));
        }
    }

    public long getThreadId()
    {
        return threadId;
    }

    public void setThreadId(long threadId)
    {
        this.threadId = threadId;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getCompiler()
    {
        return compiler;
    }

    public void setCompiler(String compiler)
    {
        this.compiler = compiler;
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

    public int getCompileId()
    {
        return compileId;
    }

    public void setCompileId(int compileId)
    {
        this.compileId = compileId;
    }
}
