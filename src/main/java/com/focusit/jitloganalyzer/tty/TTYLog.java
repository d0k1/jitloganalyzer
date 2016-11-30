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

    public Map<Class, Set<String>> attrsByType = new HashMap<>();

    private List<TTYEvent> events = new ArrayList<>();
    private List<ClassLoadEvent> classLoading = new ArrayList<>();
    private HashMap<Long, List<TTYEvent>> jitCompilations = new HashMap<>();
    private List<SweeperEvent> sweeping = new ArrayList<>();
    private HashMap<String, List<Long>> methodCompilations = new HashMap<>();

    private Comparator<TTYEvent> ttyEventComparatorByStamp = (o1, o2) -> {
        if (o1 instanceof HasStamp && o2 instanceof HasStamp)
        {
            double diff = (((HasStamp)o2).getStamp() - ((HasStamp)o1).getStamp());
            int result = 0;
            if (diff > 0)
                result = 1;
            if (diff < 0)
                result = -1;

            return result;
        }
        return -1;
    };

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
                    if (event instanceof AbstractTTYEvent)
                    {
                        Set<String> attrs = attrsByType.get(event.getClass());
                        if (attrs == null)
                        {
                            attrs = new HashSet<>();
                            attrsByType.put(event.getClass(), attrs);
                        }
                        attrs.addAll(((AbstractTTYEvent)event).getAttributes(line).keySet());
                    }
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
        events.forEach(event -> {
            if (event instanceof ClassLoadEvent)
            {
                classLoading.add((ClassLoadEvent)event);
            }
        });

        classLoading.sort(ttyEventComparatorByStamp);
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
                compilationEvents.sort(ttyEventComparatorByStamp);
            }
        });
    }

    public void fillSweeping()
    {
        events.forEach(event -> {
            if (event instanceof SweeperEvent)
            {
                sweeping.add((SweeperEvent)event);
            }
        });
        sweeping.sort(ttyEventComparatorByStamp);
    }

    public void fillMethodCompilations()
    {
        events.forEach(event -> {
            if (event instanceof TaskQueuedEvent)
            {
                String method = ((HasMethod)event).getMethod();
                List<Long> compilations = methodCompilations.get(method);
                if (compilations == null)
                {
                    compilations = new ArrayList<>();
                    methodCompilations.put(method, compilations);
                }
                compilations.add(((HasCompileId)event).getCompileId());
            }
        });
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
        log.fillClassLoading();
        log.fillSweeping();
        log.fillMethodCompilations();
        log.fillJitCompilations();

        System.out.println("Done. Got " + log.getEventLog().size() + " events");
    }
}
