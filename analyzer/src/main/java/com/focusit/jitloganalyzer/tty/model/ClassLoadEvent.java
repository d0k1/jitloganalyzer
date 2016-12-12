package com.focusit.jitloganalyzer.tty.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 08.11.16.
 *
 * [Loaded org.apache.catalina.Server from file:/home/administrator/stress/tomcat/lib/catalina.jar]
 * [Loaded sun.reflect.MagicAccessorImpl from /opt/jdk1.8.0_60/jre/lib/rt.jar]
 *
 */
public class ClassLoadEvent extends AbstractTTYEvent
{
    private final static String START_TOKEN = "[Loaded";
    private final static Pattern pattern = Pattern.compile("\\[Loaded.(.+).from.(.+)\\]");
    private String classname;
    private String jarname;

    public String getClassname()
    {
        return classname;
    }

    public void setClassname(String classname)
    {
        this.classname = classname;
    }

    public String getJarname()
    {
        return jarname;
    }

    public void setJarname(String jarname)
    {
        this.jarname = jarname;
    }

    @Override
    public boolean suitable(String line)
    {
        return line.startsWith(START_TOKEN);
    }

    @Override
    public void processLine(String line)
    {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find())
        {
            classname = matcher.group(1);
            jarname = matcher.group(2);
        }
    }
}
