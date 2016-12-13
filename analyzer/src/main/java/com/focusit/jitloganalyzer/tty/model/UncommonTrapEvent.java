package com.focusit.jitloganalyzer.tty.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doki on 08.11.16.
 *
 * <uncommon_trap thread='139673775703808' reason='unstable_if' action='reinterpret' compile_id='354' compiler='C2' level='4' stamp='0,324'>
 *    <jvms bci='563' method='com/sun/org/apache/xerces/internal/impl/XMLEntityScanner scanLiteral (ILcom/sun/org/apache/xerces/internal/xni/XMLString;)I' bytes='714' count='1086' backedge_count='26662' iicount='1086'/>
 * </uncommon_trap>
 */
public class UncommonTrapEvent extends AbstractTTYEvent implements TTYEvent, HasCompileId, HasThreadId, HasStamp
{
    private final static String START_TOKEN = "<uncommon_trap";

    public void addJVMS(JVMSEvent e)
    {
        List<JVMSEvent> events = (List<JVMSEvent>)attributes.get("jvms");
        if (events == null)
        {
            events = new ArrayList<>();
            attributes.put("jvms", events);
        }
        events.add(e);
    }

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

    public long getThreadId()
    {
        return Long.parseLong((String)attributes.get("thread"));
    }

    public long getCompileId()
    {
        return Long.parseLong((String)attributes.get("compile_id"));
    }
}
