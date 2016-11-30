package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 * <nmethod compile_id='28' compiler='C1' level='3' entry='0x00007f083d115320' size='1624' address='0x00007f083d115190' relocation_offset='296' insts_offset='400' stub_offset='1136' scopes_data_offset='1320' scopes_pcs_offset='1456' dependencies_offset='1600' nul_chk_table_offset='1608' method='java/lang/String lastIndexOf (II)I' bytes='52' count='175' backedge_count='3909' iicount='175' stamp='0,124'/>
 */
public class NMethodEvent extends AbstractTTYEvent implements TTYEvent, HasCompileId, HasCompiler, HasStamp, HasMethod
{
    private final static String START_TOKEN = "<nmethod";

    public long getCompileId()
    {
        return Long.parseLong(attributes.get("compile_id"));
    }

    public String getCompiler()
    {
        return attributes.get("compiler");
    }

    public double getStamp()
    {
        return Double.parseDouble(attributes.get("stamp").replace(",", "."));
    }

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

    public String getMethod()
    {
        return attributes.get("method");
    }

}
