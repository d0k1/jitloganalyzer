package com.focusit.jitloganalyzer.compilation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Created by doki on 11.12.16.
 */
public class CompilationLog
{
    public static final String TOKEN_TASK_START = "<task ";
    public static final String TOKEN_TASK_STOP = "</task>";
    private final Pattern pattern = Pattern.compile("compile_id='(\\d+)'");
    private Map<Long, CompilerTask> compilerTasks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<CompletableFuture, Object> futures = new ConcurrentHashMap<>();
    private ConcurrentHashMap<CompletableFuture, Object> innerFutures = new ConcurrentHashMap<>();

    private long getTaskCompileId(String taskThread)
    {
        Matcher m = pattern.matcher(taskThread);
        if (taskThread.startsWith(TOKEN_TASK_START))
        {
            if (m.find())
            {
                return Long.parseLong(m.group(1));
            }
        }
        return -1;
    }

    public void processLog(CompilerParser parser, List<String> xml)
    {
        System.out.println(parser.getParserName() + " Log " + xml.size() + " lines");

        long compileId = -1;

        boolean taskFound = false;

        List<String> taskContent = new ArrayList<>();

        for (int i = 0; i < xml.size(); i++)
        {
            String line = xml.get(i);
            if (line.startsWith(TOKEN_TASK_START))
            {
                taskFound = true;
                compileId = getTaskCompileId(line);
                if (compileId < 0)
                {
                    throw new IllegalArgumentException("No compile id found in " + line);
                }
            }
            if (taskFound == true)
            {
                taskContent.add(line);
            }
            if (taskFound == true && line.startsWith(TOKEN_TASK_STOP))
            {
                taskFound = false;
                computeCompilerTask(parser, taskContent, compileId);
                compileId = -1;
                taskContent = new ArrayList<>();
            }
        }
    }

    private void computeCompilerTask(CompilerParser parser, List<String> taskContent, long compileId)
    {
        innerFutures.put(CompletableFuture.runAsync(() -> {
            CompilerTask task = null;
            try
            {
                task = parser.getCompilerTask(compileId, taskContent);
            }
            catch (ParserConfigurationException e)
            {
                e.printStackTrace();
            }
            catch (SAXException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            compilerTasks.put(compileId, task);
        }), new Object());
    }

    public void parseLog(String filename) throws IOException, ParserConfigurationException, SAXException
    {
        ArrayList<String> lines = new ArrayList<>();
        boolean started = false;

        boolean c1log = false;
        boolean c2log = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename), 64 * 1024 * 1024))
        {
            for (String line; (line = br.readLine()) != null;)
            {
                if (line.startsWith("<compilation_log "))
                {
                    started = true;
                }
                if (started)
                {
                    if (line.startsWith("<start_compile_thread name='C1"))
                    {
                        c1log = true;
                    }
                    if (line.startsWith("<start_compile_thread name='C2"))
                    {
                        c2log = true;
                    }
                    lines.add(line);
                }

                if (line.startsWith("</compilation_log>"))
                {
                    started = false;
                    if (c1log)
                    {
                        ArrayList<String> finalLines = lines;
                        futures.put(CompletableFuture.runAsync(() -> processLog(new C1LogParser(), finalLines)),
                                new Object());
                    }
                    if (c2log)
                    {
                        ArrayList<String> finalLines1 = lines;
                        futures.put(CompletableFuture.runAsync(() -> processLog(new C2LogParser(), finalLines1)),
                                new Object());
                    }
                    c1log = false;
                    c2log = false;
                    lines = new ArrayList<>();
                }
            }
        }

        // waiting for futures
        futures.keySet().forEach(future -> {
            try
            {
                future.get();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        });

        // waiting for inner futures
        innerFutures.keySet().forEach(future -> {
            try
            {
                future.get();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        });
    }

    public CompilerTask get(long id)
    {
        return compilerTasks.get(id);
    }
}
