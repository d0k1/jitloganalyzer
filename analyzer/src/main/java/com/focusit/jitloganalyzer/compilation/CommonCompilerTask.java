package com.focusit.jitloganalyzer.compilation;

/**
 * Created by doki on 14.12.16.
 */
public class CommonCompilerTask implements CompilerTask
{
    private long compileId;
    private String content;

    public CommonCompilerTask(long compileId, String content)
    {
        this.compileId = compileId;
        this.content = content;
    }

    @Override
    public long getCompileId()
    {
        return compileId;
    }

    @Override
    public String getContent()
    {
        return content;
    }
}
