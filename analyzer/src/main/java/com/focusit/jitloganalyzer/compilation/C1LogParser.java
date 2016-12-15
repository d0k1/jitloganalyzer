package com.focusit.jitloganalyzer.compilation;

import java.util.List;

/**
 * Created by doki on 14.12.16.
 */
public class C1LogParser implements CompilerParser
{
    @Override
    public String getParserName()
    {
        return "C1";
    }

    @Override
    public CompilerTask getCompilerTask(long compileId, List<String> content)
    {
        StringBuilder builder = new StringBuilder();
        content.forEach(line -> builder.append(line).append("\n"));
        return new C1CompilerTask(compileId, builder.toString());
    }
}
