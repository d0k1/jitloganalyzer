package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 * <nmethod compile_id='28' compiler='C1' level='3' entry='0x00007f083d115320' size='1624' address='0x00007f083d115190' relocation_offset='296' insts_offset='400' stub_offset='1136' scopes_data_offset='1320' scopes_pcs_offset='1456' dependencies_offset='1600' nul_chk_table_offset='1608' method='java/lang/String lastIndexOf (II)I' bytes='52' count='175' backedge_count='3909' iicount='175' stamp='0,124'/>
 */
public class NMethodEvent implements TTYEvent
{
    private final static String START_TOKEN = "<nmethod";

    private long compileId;
    private String compiler;
    private int level;
    private String entry;
    private int size;
    private String address;
    private String method;
    private int bytes;
    private int count;
    private int iicount;
    private double stamp;

    public long getCompileId()
    {
        return compileId;
    }

    public void setCompileId(long compileId)
    {
        this.compileId = compileId;
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

    public String getEntry()
    {
        return entry;
    }

    public void setEntry(String entry)
    {
        this.entry = entry;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public int getBytes()
    {
        return bytes;
    }

    public void setBytes(int bytes)
    {
        this.bytes = bytes;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getIicount()
    {
        return iicount;
    }

    public void setIicount(int iicount)
    {
        this.iicount = iicount;
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

    }
}
