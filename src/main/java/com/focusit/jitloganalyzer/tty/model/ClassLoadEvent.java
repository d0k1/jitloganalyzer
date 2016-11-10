package com.focusit.jitloganalyzer.tty.model;

/**
 * Created by doki on 08.11.16.
 *
 * [Loaded org.apache.catalina.Server from file:/home/administrator/stress/tomcat/lib/catalina.jar]
 * [Loaded sun.reflect.MagicAccessorImpl from /opt/jdk1.8.0_60/jre/lib/rt.jar]
 *
 */
public class ClassLoadEvent implements TTYEvent
{
    public final static String START_TOKEN = "[Loaded";

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
    public void processLine(String line)
    {

    }
}
