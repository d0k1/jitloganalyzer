package com.focusit.jitloganalyzer.model.tty.events;

/**
 * Created by doki on 08.11.16.
 *
 * <uncommon_trap thread='139673775703808' reason='unstable_if' action='reinterpret' compile_id='354' compiler='C2' level='4' stamp='0,324'>
 *    <jvms bci='563' method='com/sun/org/apache/xerces/internal/impl/XMLEntityScanner scanLiteral (ILcom/sun/org/apache/xerces/internal/xni/XMLString;)I' bytes='714' count='1086' backedge_count='26662' iicount='1086'/>
 * </uncommon_trap>
 */
public class UncommonTrapEvent implements TTYEvent
{
    private long threadId;
    private String reason;
    private String action;
    private String compiler;
    private int level;
    private double stamp;
}
