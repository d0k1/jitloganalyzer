package com.focusit.jitloganalyzer.model.tty.events;

/**
 * Created by doki on 08.11.16.
 *
 * [Loaded org.apache.catalina.Server from file:/home/administrator/stress/tomcat/lib/catalina.jar]
 * [Loaded sun.reflect.MagicAccessorImpl from /opt/jdk1.8.0_60/jre/lib/rt.jar]
 *
 */
public class ClassLoadEvent implements TTYEvent
{
    private String classname;
    private String jarname;
}
