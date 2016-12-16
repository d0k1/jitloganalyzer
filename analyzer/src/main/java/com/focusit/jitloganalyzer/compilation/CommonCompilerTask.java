package com.focusit.jitloganalyzer.compilation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import groovy.util.slurpersupport.GPathResult;

/**
 * Created by doki on 14.12.16.
 */
public class CommonCompilerTask implements CompilerTask
{
    private long compileId;
    private List<String> content;
    private GPathResult xml;

    public CommonCompilerTask(long compileId, List<String> content)
            throws ParserConfigurationException, SAXException, IOException
    {
        this.compileId = compileId;
        this.content = new ArrayList<>(content);
    }

    @Override
    public long getCompileId()
    {
        return compileId;
    }

    @Override
    public List<String> getContent()
    {
        return content;
    }

    public GPathResult getXml()
    {
        return xml;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        content.forEach(line -> builder.append(line).append("\n"));

        return builder.toString();
    }
}
