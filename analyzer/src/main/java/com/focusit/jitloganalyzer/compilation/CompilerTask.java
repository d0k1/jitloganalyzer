package com.focusit.jitloganalyzer.compilation;

import com.focusit.jitloganalyzer.tty.model.HasCompileId;

/**
 * Created by doki on 14.12.16.
 */
public interface CompilerTask extends HasCompileId
{
    String getContent();
}
