package com.focusit.jitloganalyzer.tty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.focusit.jitloganalyzer.tty.model.*;

/**
 * Created by doki on 08.11.16.
 */
public class TTYLog
{
    private boolean started = false;

    private List<TTYEvent> events = new ArrayList<>();
    private List<ClassLoadEvent> classLoading = new ArrayList<>();
    private HashMap<Long, List<TTYEvent>> jitCompilations = new HashMap<>();
    private List<SweeperEvent> sweeping = new ArrayList<>();
    private HashMap<String, List<TTYEvent>> methodEvents = new HashMap<>();
    private HashMap<String, List<Integer>> methodCompilations = new HashMap<>();

    public void parseLog(String filename) throws IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            for (String line; (line = br.readLine()) != null;)
            {
                if (!started && line.toLowerCase().equals("<tty>"))
                {
                    started = true;
                    continue;
                }

                TTYEvent event = TTYEventFactory.getEventForString(line);

                if (event != null)
                {
                    event.processLine(line);
                    events.add(event);
                }

                if (started && line.toLowerCase().equals("</tty>"))
                {
                    break;
                }

            }
        }
    }

    public void fillClassLoading()
    {

    }

    public void fillJitCompilations()
    {
        events.forEach(event -> {
            if (event instanceof NMethodEvent || event instanceof TaskQueuedEvent || event instanceof UncommonTrapEvent)
            {
                HasCompileId hci = (HasCompileId)event;
                if (jitCompilations.get(hci.getCompileId()) == null)
                {
                    jitCompilations.put(hci.getCompileId(), new ArrayList<>());
                }
                List<TTYEvent> compilationEvents = jitCompilations.get(hci.getCompileId());
                compilationEvents.add(event);
            }
        });
    }

    public void fillSweeping()
    {

    }

    public void fillMethodEvents()
    {

    }

    public void fillMethodCompilations()
    {

    }

    public Collection<TTYEvent> getEventLog()
    {
        return Collections.unmodifiableList(events);
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("JIT LOG TTY Events");

        TTYLog log = new TTYLog();

        log.parseLog(args[0]);
        log.fillJitCompilations();

        System.out.println("Done. Got " + log.getEventLog().size() + " events");
    }
}
