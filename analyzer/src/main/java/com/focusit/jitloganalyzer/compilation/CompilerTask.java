package com.focusit.jitloganalyzer.compilation;

import java.util.List;

import com.focusit.jitloganalyzer.tty.model.HasCompileId;

/**
 * Created by doki on 14.12.16.
 */
public interface CompilerTask extends HasCompileId
{
    String toString();

    List<String> getContent();
}
