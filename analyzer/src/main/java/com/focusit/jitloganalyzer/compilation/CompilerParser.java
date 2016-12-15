package com.focusit.jitloganalyzer.compilation;

import java.util.List;

/**
 * Created by doki on 14.12.16.
 */
public interface CompilerParser
{
    String getParserName();

    CompilerTask getCompilerTask(long compileId, List<String> content);
}
