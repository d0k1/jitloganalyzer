package com.focusit.jitloganalyzer.compilation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;

/**
 * Created by doki on 11.12.16.
 */
public class CompilationLog
{
    public static void main(String[] args)
    {

    }

    public void processLog(String xml) throws ParserConfigurationException, SAXException, IOException
    {
        XmlSlurper slurper = new XmlSlurper();
        GPathResult result = slurper.parseText(xml);
        System.out.println(result);
    }

    public void parseLog(String filename) throws IOException, ParserConfigurationException, SAXException
    {
        StringBuilder builder = new StringBuilder();

        boolean started = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            for (String line; (line = br.readLine()) != null;)
            {
                if (line.startsWith("<compilation_log "))
                {
                    started = true;
                }
                if (started)
                {
                    builder.append(line).append("\n");
                }

                if (line.startsWith("</compilation_log>"))
                {
                    started = false;
                    processLog(builder.toString());
                }
            }
        }
    }
}
