package com.focusit.jitloganalyzer;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

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

        TTYLog tty = new TTYLog();

        SwingUtilities.invokeLater(() -> new AnalyzerMainFrame(tty.events, tty.classLoading, tty.queued_tasks,
                tty.sweeping, tty.methodCompilations, cl, aVoid -> {
                    try
                    {
                        try
                        {
                            tty.parseLog(args[0]);
                            CompletableFuture.allOf(CompletableFuture.runAsync(tty::fillClassLoading),
                                    CompletableFuture.runAsync(tty::fillSweeping),
                                    CompletableFuture.runAsync(tty::fillMethodCompilations),
                                    CompletableFuture.runAsync(tty::fillJitCompilations));
                            cl.parseLog(args[0]);
                        }
                        catch (ParserConfigurationException e)
                        {
                            e.printStackTrace();
                        }
                        catch (SAXException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    return null;
                }));
    }
}
