package com.focusit.jitloganalyzer;

import java.io.IOException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.focusit.jitloganalyzer.compilation.CompilationLog;
import com.focusit.jitloganalyzer.tty.TTYLog;

/**
 * Created by doki on 13.12.16.
 */
public class App
{
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
    {
        CompilationLog cl = new CompilationLog();
        cl.parseLog(args[0]);

        TTYLog tty = new TTYLog();

        SwingUtilities.invokeLater(() -> new AnalyzerMainFrame(tty.events, tty.classLoading, tty.jitCompilations,
                tty.sweeping, tty.methodCompilations, aVoid -> {
                    try
                    {
                        tty.parseLog(args[0]);
                        tty.fillClassLoading();
                        tty.fillSweeping();
                        tty.fillMethodCompilations();
                        tty.fillJitCompilations();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    return null;
                }));
    }
}
