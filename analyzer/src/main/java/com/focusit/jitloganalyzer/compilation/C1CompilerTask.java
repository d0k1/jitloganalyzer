package com.focusit.jitloganalyzer.compilation;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Created by doki on 14.12.16.
 */
public class C1CompilerTask extends CommonCompilerTask
{
    public C1CompilerTask(long compileId, List<String> content)
            throws IOException, SAXException, ParserConfigurationException
    {
        super(compileId, content);
    }
}
