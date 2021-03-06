package com.focusit.jitloganalyzer.compilation;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Created by doki on 14.12.16.
 */
public class C2LogParser implements CompilerParser
{
    @Override
    public String getParserName()
    {
        return "C2";
    }

    @Override
    public CompilerTask getCompilerTask(long compileId, List<String> content)
            throws ParserConfigurationException, SAXException, IOException
    {
        return new C2CompilerTask(compileId, content);
    }
}
