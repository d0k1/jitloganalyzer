package com.focusit.jitloganalyzer.compilation;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import groovy.util.slurpersupport.GPathResult;

/**
 * Created by doki on 14.12.16.
 */
public class CommonCompilerTask implements CompilerTask
{
    private long compileId;
    private String content;
    private GPathResult xml;

    public CommonCompilerTask(long compileId, String content)
            throws ParserConfigurationException, SAXException, IOException
    {
        this.compileId = compileId;
        this.content = content;
        //        XmlSlurper xmlSlurper = new XmlSlurper(false, false);
        //        xml = xmlSlurper.parseText(content);
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

    public GPathResult getXml()
    {
        return xml;
    }
}
